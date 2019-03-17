package com.qontak.assignment.mainpage

import android.util.Log
import com.google.gson.Gson
import com.qontak.assignment.Constants
import com.qontak.assignment.datamodel.Movie
import org.json.JSONObject



class MainActivityPresenter(private var mainView: MainView?, private val mainInteractor: MainActivityInteractor)
    : MainActivityInteractor.OnFinishedListener {

    private var pageIndex: Int = 0

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


    fun getData(filter: String) {

        //set page index to 1 for initial
        pageIndex = 1

        //show progress bar in main activity
        mainView?.showProgressBar()

        //hide load more button
        mainView?.hideLoadMoreButton()

        //hide recycler view while loading data
        mainView?.hideRecyclerView()

        when (filter) {
            Constants.FILTER_POPULAR -> mainInteractor.getPopularMovies(this, pageIndex)
            Constants.FILTER_TOP_RATED -> mainInteractor.getTopRatedMovies(this, pageIndex)
            else -> mainInteractor.searchMovieByTitle(this, pageIndex, filter)
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