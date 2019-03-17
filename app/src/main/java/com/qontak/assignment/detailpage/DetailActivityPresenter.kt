package com.qontak.assignment.detailpage

import android.util.Log
import com.google.gson.Gson
import com.qontak.assignment.datamodel.MovieCredit
import com.qontak.assignment.datamodel.MovieDetail

class DetailActivityPresenter(
    private var detailView: DetailView?,
    private val detailInteractor: DetailActivityInteractor
) : DetailActivityInteractor.OnFinishedListener {

    override fun onResultSuccess(jsonData: String) {
        detailView?.displayMovieDetail(convertJsonToMovieDetail(jsonData))
    }

    override fun onCreditResultSuccess(jsonData: String) {
        detailView?.showCastList(convertJsonToCredit(jsonData).cast)
    }

    override fun onResultFail() {
        Log.e("result", "fail")
    }

    fun getData(id: Int) {
        detailInteractor.getMovieDetail(this, id)
    }

    fun getCreditData(id: Int) {
        detailInteractor.getCredit(this, id)
    }

    private fun convertJsonToMovieDetail(jsonData: String): MovieDetail {
        val gson = Gson()
        return gson.fromJson(jsonData, MovieDetail::class.java)
    }

    private fun convertJsonToCredit(jsonData: String): MovieCredit {
        val gson = Gson()
        return gson.fromJson(jsonData, MovieCredit::class.java)
    }

    fun onDestoy() {
        detailView = null
    }
}