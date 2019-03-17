package com.qontak.assignment.mainpage

import android.util.Log
import com.google.gson.Gson
import com.qontak.assignment.Constants
import com.qontak.assignment.datamodel.Movie
import org.json.JSONObject



class MainActivityPresenter(private var mainView: MainView?, private val mainInteractor: MainActivityInteractor)
    : MainActivityInteractor.OnFinishedListener {

    private var pageIndex: Int = 0
    private var savedFilter: String = ""

    override fun onResultSuccess(jsonData: String) {

        //pass processed data to main activity
        mainView?.showList(convertJsonToArrayList(jsonData), pageIndex > 1)

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

        //save filter for later use to get more data
        savedFilter = filter

        //show progress bar in main activity
        mainView?.showProgressBar()

        //hide load more button
        mainView?.hideLoadMoreButton()

        //hide recycler view while loading data
        mainView?.hideRecyclerView()

        //get movie data based on filter or search query
        when (filter) {
            Constants.FILTER_POPULAR -> mainInteractor.getPopularMovies(this, pageIndex)
            Constants.FILTER_TOP_RATED -> mainInteractor.getTopRatedMovies(this, pageIndex)
            else -> mainInteractor.searchMovieByTitle(this, pageIndex, filter)
        }
    }

    fun getMoreData() {

        //increment page index by 1
        pageIndex++

        //show progress bar in main activity
        mainView?.showProgressBar()

        //hide load more button
        mainView?.hideLoadMoreButton()

        //get movie data based on filter or search query
        when (savedFilter) {
            Constants.FILTER_POPULAR -> mainInteractor.getPopularMovies(this, pageIndex)
            Constants.FILTER_TOP_RATED -> mainInteractor.getTopRatedMovies(this, pageIndex)
            else -> mainInteractor.searchMovieByTitle(this, pageIndex, savedFilter)
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