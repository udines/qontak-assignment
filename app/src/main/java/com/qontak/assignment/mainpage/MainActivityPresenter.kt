package com.qontak.assignment.mainpage

import android.util.Log
import com.google.gson.Gson
import com.qontak.assignment.Constants
import com.qontak.assignment.datamodel.Movie
import org.json.JSONObject



class MainActivityPresenter(private var mainView: MainView?, private val mainInteractor: MainActivityInteractor)
    : MainActivityInteractor.OnFinishedListener {

    override fun onResultSuccess(jsonData: String) {
        mainView?.showList(convertJsonToArrayList(jsonData))
    }

    override fun onResultFail() {
        Log.e("result", "fail")
    }

    fun getData(page: Int, filter: String) {
        when (filter) {
            Constants.FILTER_POPULAR -> mainInteractor.getPopularMovies(this, page)
            else -> mainInteractor.searchMovieByTitle(this, page, filter)
        }
    }

    fun convertJsonToArrayList(jsonData: String): ArrayList<Movie> {

        val jsonObject = JSONObject(jsonData)
        val jsonArray = jsonObject.getJSONArray("results")

        val movieList = ArrayList<Movie>()

        for (i in 0 until jsonArray.length()) {
            val jObject = jsonArray.getJSONObject(i)
            val gson = Gson()
            val movie = gson.fromJson(jObject.toString(), Movie::class.java)
            movieList.add(movie)
        }

        return movieList
    }

    fun onDestroy() {
        mainView = null
    }
}