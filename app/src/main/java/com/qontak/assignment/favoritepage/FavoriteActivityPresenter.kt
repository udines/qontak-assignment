package com.qontak.assignment.favoritepage

import android.util.Log
import com.google.gson.Gson
import com.qontak.assignment.datamodel.AccountData
import com.qontak.assignment.datamodel.Movie
import com.qontak.assignment.datamodel.MovieList

class FavoriteActivityPresenter(
    private var favoriteView: FavoriteView?,
    private val favoriteInteractor: FavoriteActivityInteractor
) : FavoriteActivityInteractor.OnFinishedListener {

    private var pageIndex: Int = 0
    private var sessionId = ""
    private var accountId = 0

    override fun onGetAccountDetailSuccess(jsonData: String) {
        val accountData = parseJsonToAccountData(jsonData)
        accountId = accountData.id
        Log.d("account id", accountId.toString())
        getFavoriteMovies()
    }

    override fun onGetMoviesSuccess(jsonData: String) {
        favoriteView?.showList(convertJsonToArrayList(jsonData))
        favoriteView?.hideProgressBar()
    }

    override fun onResultFail() {
        Log.e("result", "fail")
    }

    private fun parseJsonToAccountData(jsonData: String): AccountData {
        val gson = Gson()
        return gson.fromJson(jsonData, AccountData::class.java)
    }

    fun getAccountDetail(sessionId: String) {
        this.sessionId = sessionId
        favoriteInteractor.getAccountDetail(this, sessionId)
    }

    private fun getFavoriteMovies() {
        pageIndex = 1
        favoriteView?.showProgressBar()
        favoriteInteractor.getFavoriteMovies(this, accountId, sessionId, pageIndex)
    }

    private fun convertJsonToArrayList(jsonData: String): ArrayList<Movie> {
        val gson = Gson()
        val movieList = gson.fromJson(jsonData, MovieList::class.java)
        return ArrayList(movieList.results)
    }

    fun onDestroy() {
        favoriteView = null
    }
}