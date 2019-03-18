package com.qontak.assignment.authentication

import android.util.Log
import com.google.gson.Gson
import com.qontak.assignment.datamodel.CreateSessionBody
import com.qontak.assignment.datamodel.CreateSessionResponse
import com.qontak.assignment.datamodel.RequestTokenResponse
import com.qontak.assignment.datamodel.RequestTokenWithLoginBody
import okhttp3.MediaType
import okhttp3.RequestBody


class LoginPresenter(
    private var loginView: LoginView?,
    private val loginInteractor: LoginInteractor
) : LoginInteractor.OnFinishedListener {

    private var username: String = ""
    private var password: String = ""
    private var requestToken: String = ""

    override fun onRequestTokenSuccess(jsonData: String) {

        val requestToken = parseJsonToRequestToken(jsonData).requestToken
        this.requestToken = requestToken

        loginInteractor.requestTokenWithLogin(
            this,
            createLoginRequestBody(
                username,
                password,
                requestToken
            )
        )
    }

    override fun onRequestTokenFailed() {

    }

    override fun onRequestTokenWithLoginSuccess(jsonData: String) {
        loginInteractor.createSession(
            this,
            createSessionRequestBody(requestToken)
        )
    }

    override fun onRequestTokenWithLoginFail() {

    }

    override fun onRequestSessionSuccess(jsonData: String) {

        //save session id using SharedPreferences
        val sessionResponse = parseJsonToSession(jsonData)

        loginView?.finishActivity(sessionResponse.sessionId)
    }

    override fun onRequestSessionFailed() {

    }

    fun login(username: String, password: String) {
        this.username = username
        this.password = password
        loginInteractor.requestToken(this)
    }

    fun onDestroy() {
        loginView = null
    }

    private fun createLoginRequestBody(username: String, password: String, requestToken: String): RequestBody {
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val loginReqBody = RequestTokenWithLoginBody(password, requestToken, username)
        val gson = Gson()
        Log.d("login post body", gson.toJson(loginReqBody))
        return RequestBody.create(mediaType, gson.toJson(loginReqBody))
    }

    private fun createSessionRequestBody(requestToken: String): RequestBody {
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val sessionReqBody = CreateSessionBody(requestToken)
        val gson = Gson()
        return RequestBody.create(mediaType, gson.toJson(sessionReqBody))
    }

    private fun parseJsonToRequestToken(jsonData: String): RequestTokenResponse {
        val gson = Gson()
        return gson.fromJson(jsonData, RequestTokenResponse::class.java)
    }

    private fun parseJsonToSession(jsonData: String): CreateSessionResponse {
        val gson = Gson()
        return gson.fromJson(jsonData, CreateSessionResponse::class.java)
    }
}