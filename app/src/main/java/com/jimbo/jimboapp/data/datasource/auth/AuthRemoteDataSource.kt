package com.jimbo.jimboapp.data.datasource.auth

import com.jimbo.jimboapp.data.api.AuthApi
import com.jimbo.jimboapp.data.api.AuthBody
import com.jimbo.jimboapp.data.api.NetworkProvider
import com.jimbo.jimboapp.data.response.LoginResponse
import kotlin.Exception

class AuthRemoteDataSource constructor(
    private val authApi: AuthApi = NetworkProvider.authApi
) {

    suspend fun login(
        email: String,
        password: String
    ): Result<LoginResponse> {
        return try {
            val response = authApi.login(
                AuthBody(
                    email,
                    password
                )
            )

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception())
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

}