package com.jimbo.jimboapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimbo.jimboapp.data.datasource.auth.AuthRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    var loginState: StateFlow<LoginState> = _loginState

    private val _authRequestState: MutableSharedFlow<AuthRequestStatus> = MutableSharedFlow()
    var authRequestState = _authRequestState

    private var authRemoteDataSource: AuthRemoteDataSource = AuthRemoteDataSource()

    fun updateUsername(
        username: String
    ) = _loginState.update {
        LoginState(
            username = username,
            password = it.password,
            loading = false
        )
    }

    fun updatePassword(
        password: String
    ) = _loginState.update {
        LoginState(
            username = it.username,
            password = password,
            loading = false
        )
    }

    fun login() {
        _loginState.update {
            it.copy(
                loading = true
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            val result = authRemoteDataSource
                .login(
                    loginState.value.username,
                    loginState.value.password
                )

            when (result.isSuccess) {
                true -> {
                    _authRequestState.emit(
                        AuthRequestStatus.Success(result.getOrNull()!!.data)
                    )
                }
                false -> {
                    _authRequestState.emit(AuthRequestStatus.Error("Error"))
                }
            }

            _loginState.update {
                it.copy(
                    loading = false
                )
            }
        }
    }

}