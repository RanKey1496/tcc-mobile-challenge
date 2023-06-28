package com.jimbo.jimboapp.data.api

import com.jimbo.jimboapp.data.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun login(
        @Body body: AuthBody
    ): Response<LoginResponse>
}

data class AuthBody(
    val email: String,
    val password: String
)