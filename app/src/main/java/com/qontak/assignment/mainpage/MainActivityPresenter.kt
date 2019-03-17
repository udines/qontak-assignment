package com.qontak.assignment.mainpage

import android.util.Log
import com.google.gson.Gson
import com.qontak.assignment.Constants
import com.qontak.assignment.datamodel.Movie
import org.json.JSONObject



class MainActivityPresenter(private var mainView: MainView?, private val mainInteractor: MainActivityInteractor)
    : MainActivityInteractor.OnFinishedListener {

    override fun onResultSuccess(jsonData: String) {

        //pass processed data to main activity
        mainView?.showList(convertJsonToArrayList(jsonData))

        //hide progress bar
        mainView?.hideProgressBar()

        //show load more button
        mainView?.showLoadMoreButton()
    }

    override fun onResultFail() {
        Log.e("result", "fail")
    }

    fun getData(page: Int, filter: String) {

        //show progress bar in main activity
        mainView?.showProgressBar()

        //hide load more button
        mainView?.hideLoadMoreButton()

        //hide recycler view while loading data
        mainView?.hideRecyclerView()

        when (filter) {
            Constants.FILTER_POPULAR -> mainInteractor.getPopularMovies(this, page)
            Constants.FILTER_TOP_RATED -> mainInteractor.getTopRatedMovies(this, page)
            else -> mainInteractor.searchMovieByTitle(this, page, filter)
        }
    }

    private fun convertJsonToArrayList(jsonData: String): ArrayList<Movie> {

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