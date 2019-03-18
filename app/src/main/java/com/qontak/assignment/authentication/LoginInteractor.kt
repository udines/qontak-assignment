package com.qontak.assignment.authentication

import android.util.Log
import com.google.gson.Gson
import com.qontak.assignment.Constants
import okhttp3.*
import java.io.IOException


class LoginInteractor {
    interface OnFinishedListener {
        fun onRequestTokenSuccess(jsonData: String)
        fun onRequestTokenFailed()
        fun onRequestTokenWithLoginSuccess(jsonData: String)
        fun onRequestTokenWithLoginFail()
        fun onRequestSessionSuccess(jsonData: String)
        fun onRequestSessionFailed()
    }

    fun requestToken(onFinishedListener: OnFinishedListener) {

        val url = Constants.BASE_URL_API + "authentication/token/new?api_key=" + Constants.API_KEY

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFinishedListener.onRequestTokenFailed()
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body()?.string()
                if (jsonData != null && response.code() == 200) {
                    onFinishedListener.onRequestTokenSuccess(jsonData)
                } else {
                    Log.d("request token", response.code().toString())
                }
            }
        })
    }

    fun requestTokenWithLogin(onFinishedListener: OnFinishedListener, body: RequestBody) {

        val url = Constants.BASE_URL_API + "authentication/token/validate_with_login?api_key=" + Constants.API_KEY

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                onFinishedListener.onRequestTokenWithLoginFail()
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body()?.string()
                if (jsonData != null && response.code() == 200) {
                    onFinishedListener.onRequestTokenWithLoginSuccess(jsonData)
                } else {
                    Log.d("request with login", response.code().toString())
                }
            }

        })
    }

    fun createSession(onFinishedListener: OnFinishedListener, body: RequestBody) {

        val url = Constants.BASE_URL_API + "authentication/session/new?api_key=" + Constants.API_KEY

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                onFinishedListener.onRequestSessionFailed()
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body()?.string()
                if (jsonData != null && response.code() == 200) {
                    onFinishedListener.onRequestSessionSuccess(jsonData)
                } else {
                    Log.d("create session", response.code().toString())
                }
            }

        })
    }
}