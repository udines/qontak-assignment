package com.qontak.assignment.detailpage

import android.util.Log
import com.google.gson.Gson
import com.qontak.assignment.Constants
import com.qontak.assignment.datamodel.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class DetailActivityPresenter(
    private var detailView: DetailView?,
    private val detailInteractor: DetailActivityInteractor
) : DetailActivityInteractor.OnFinishedListener {

    private var accountId = 0
    private var sessionId = ""
    private var setToFav = false
    private var mediaId = 0

    override fun onResultSuccess(jsonData: String) {

        val movieDetail = convertJsonToMovieDetail(jsonData)
        val genreString = StringBuilder()
        for (genre in movieDetail.genres) {
            genreString.append(genre.name + ", ")
        }
        detailView?.showStoryline(
            movieDetail.overview,
            movieDetail.tagline,
            removeLastChar(genreString.toString())
        )

        val subtitleString = StringBuilder()
        val countryString = StringBuilder()
        val languageString = StringBuilder()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val newDateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        val rating = movieDetail.voteAverage.toString() + " /10"
        val date = Date()

        subtitleString.append(movieDetail.runtime).append("min  ").append(removeLastChar(genreString.toString()))
        for (country in movieDetail.productionCountries) {
            countryString.append(country.name + ", ")
        }
        for (language in movieDetail.spokenLanguages) {
            languageString.append(language.name + ", ")
        }
        date.time = dateFormat.parse(movieDetail.releaseDate).time

        detailView?.showDetails(
            movieDetail.title,
            subtitleString.toString(),
            rating,
            movieDetail.status,
            newDateFormat.format(date),
            removeLastChar(countryString.toString()),
            removeLastChar(languageString.toString()),
            movieDetail.posterPath
        )
    }

    override fun onCreditResultSuccess(jsonData: String) {

        val credit = convertJsonToCredit(jsonData)
        detailView?.showCastList(credit.cast)

        val directorString = StringBuilder()
        val writerString = StringBuilder()
        for (crew in credit.crew) {
            if (crew.job == Constants.MOVIE_CREW_JOB_DIRECTOR) {
                directorString.append(crew.name + ", ")
            } else if (crew.department == Constants.MOVIE_CREW_DEPARTMENT_WRITING) {
                writerString.append(crew.name + ", ")
            }
        }
        detailView?.showCrew(removeLastChar(directorString.toString()), removeLastChar(writerString.toString()))
    }

    override fun onResultFail() {
        Log.e("result", "fail")
    }

    override fun onGetAccountDetailSuccess(jsonData: String) {
        val accountData = parseJsonToAccountData(jsonData)
        accountId = accountData.id
        Log.d("account id", accountId.toString())
        detailInteractor.setMovieAsFavorite(this, accountId, sessionId, createFavoriteRequestBody(mediaId, setToFav))
    }

    override fun onMovieFavoriteSuccess(responseCode: Int) {
        //change favorite icon color
        detailView?.changeFavIconColor(setToFav)
    }

    fun getData(id: Int) {
        detailInteractor.getMovieDetail(this, id)
    }

    fun getCreditData(id: Int) {
        detailInteractor.getCredit(this, id)
    }

    fun getAccountDetail(sessionId: String, setAsFav: Boolean, mediaId: Int) {
        Log.d("detail presenter", "get account detail")
        this.sessionId = sessionId
        this.setToFav = setAsFav
        this.mediaId = mediaId
        detailInteractor.getAccountDetail(this, sessionId)
    }

    private fun convertJsonToMovieDetail(jsonData: String): MovieDetail {
        val gson = Gson()
        return gson.fromJson(jsonData, MovieDetail::class.java)
    }

    private fun convertJsonToCredit(jsonData: String): MovieCredit {
        val gson = Gson()
        return gson.fromJson(jsonData, MovieCredit::class.java)
    }

    private fun removeLastChar(string: String): String {
        var str = string
        if (str.isNotEmpty()) {
            str = str.substring(0, str.length - 2)
        }
        return str
    }

    private fun parseJsonToAccountData(jsonData: String): AccountData {
        val gson = Gson()
        return gson.fromJson(jsonData, AccountData::class.java)
    }

    private fun createFavoriteRequestBody(mediaId: Int, fav: Boolean): RequestBody {
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val favReqBody = MovieFavoriteRequestBody(fav, mediaId, "Movie")
        val gson = Gson()
        return RequestBody.create(mediaType, gson.toJson(favReqBody))
    }

    fun onDestroy() {
        detailView = null
    }
}