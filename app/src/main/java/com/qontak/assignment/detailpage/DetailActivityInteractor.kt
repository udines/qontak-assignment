package com.qontak.assignment.detailpage

import android.util.Log
import com.qontak.assignment.Constants
import okhttp3.*
import java.io.IOException

class DetailActivityInteractor {

    interface OnFinishedListener {
        fun onResultSuccess(jsonData: String)
        fun onResultFail()
    }

    fun getMovieDetail(onFinishedListener: OnFinishedListener, id: Int) {

        val url = "https://api.themoviedb.org/3/movie/" + id.toString() + "?api_key=" + Constants.API_KEY + "&language=en-US"

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                Log.d("response code", response.code().toString())
                val jsonData = response.body()?.string()
                if (jsonData != null) {
                    onFinishedListener.onResultSuccess(jsonData)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                onFinishedListener.onResultFail()
            }

        })
    }
}