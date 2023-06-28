package com.jimbo.jimboapp.ui.login

data class LoginState(
    val username: String = "",
    val password: String = "",
    val loading: Boolean = false
)

sealed class AuthRequestStatus {
    data class Success(
        val token: String
    ) : AuthRequestStatus()
    data class Error(
        val message: String
    ) : AuthRequestStatus()
}