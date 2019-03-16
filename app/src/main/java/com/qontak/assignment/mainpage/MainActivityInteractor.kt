package com.qontak.assignment.mainpage

import android.util.Log
import com.qontak.assignment.Constants
import okhttp3.*
import java.io.IOException

class MainActivityInteractor {

    interface OnFinishedListener {
        fun onResultSuccess(jsonData: String)
        fun onResultFail()
    }

    fun getPopularMovies(onFinishedListener: OnFinishedListener, page: Int) {

        val url = "https://api.themoviedb.org/3/movie/popular?api_key=" + Constants.API_KEY + "&language=en-US&page=" + page.toString()

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

    fun getTopRatedMovies(onFinishedListener: OnFinishedListener, page: Int) {

        val url = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + Constants.API_KEY + "&language=en-US&page=" + page.toString()

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

    fun searchMovieByTitle(onFinishedListener: OnFinishedListener, page: Int, title: String) {
        val url = "https://api.themoviedb.org/3/search/movie?api_key=" + Constants.API_KEY + "&language=en-US&query=" + title + "&page=" + page.toString() + "&include_adult=false"

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
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