package com.qontak.assignment.datamodel

import com.google.gson.annotations.SerializedName

data class RequestTokenWithLoginBody(
    @SerializedName("password") val password: String,
    @SerializedName("request_token") val requestToken: String,
    @SerializedName("username") val username: String
)

data class RequestTokenResponse(
    @SerializedName("expires_at") val expiresAt: String,
    @SerializedName("request_token") val requestToken: String,
    @SerializedName("success") val success: Boolean
)

data class CreateSessionBody(
    @SerializedName("request_token") val requestToken: String
)

data class CreateSessionResponse(
    @SerializedName("session_id") val sessionId: String,
    @SerializedName("success") val success: Boolean
)