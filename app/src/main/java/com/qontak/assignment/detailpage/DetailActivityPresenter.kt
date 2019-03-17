package com.qontak.assignment.detailpage

import android.util.Log
import com.google.gson.Gson
import com.qontak.assignment.datamodel.MovieDetail

class DetailActivityPresenter(
    private var detailView: DetailView?,
    private val detailInteractor: DetailActivityInteractor
) : DetailActivityInteractor.OnFinishedListener {

    override fun onResultSuccess(jsonData: String) {
        detailView?.displayMovieDetail(convertJsonToMovieDetail(jsonData))
    }

    override fun onResultFail() {
        Log.e("result", "fail")
    }

    fun getData(id: Int) {
        detailInteractor.getMovieDetail(this, id)
    }

    private fun convertJsonToMovieDetail(jsonData: String): MovieDetail {
        val gson = Gson()
        return gson.fromJson(jsonData, MovieDetail::class.java)
    }

    fun onDestoy() {
        detailView = null
    }
}