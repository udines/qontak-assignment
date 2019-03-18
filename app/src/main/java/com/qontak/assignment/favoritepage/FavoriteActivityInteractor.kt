package com.qontak.assignment.favoritepage

import com.qontak.assignment.Constants
import okhttp3.*
import java.io.IOException

class FavoriteActivityInteractor {

    interface OnFinishedListener {
        fun onGetAccountDetailSuccess(jsonData: String)
        fun onGetMoviesSuccess(jsonData: String)
        fun onResultFail()
    }

    fun getAccountDetail(onFinishedListener: OnFinishedListener, sessionId: String) {

        val url = Constants.BASE_URL_API + "account?api_key=" + Constants.API_KEY + "&session_id=" + sessionId

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFinishedListener.onResultFail()
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body()?.string()
                if (jsonData != null && response.code() == 200) {
                    onFinishedListener.onGetAccountDetailSuccess(jsonData)
                }
            }
        })
    }

    fun getFavoriteMovies(onFinishedListener: OnFinishedListener, accountId: Int, sessionId: String, page: Int) {

        val url =
            Constants.BASE_URL_API + "account/" + accountId + "/favorite/movies?api_key=" + Constants.API_KEY + "&session_id=" + sessionId + "&language=en-US&sort_by=created_at.asc&page=" + page.toString()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFinishedListener.onResultFail()
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body()?.string()
                if (jsonData != null && response.code() == 200) {
                    onFinishedListener.onGetMoviesSuccess(jsonData)
                }
            }
        })
    }
}