package com.jimbo.jimboapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimbo.jimboapp.data.datasource.client.ClientRemoteDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _mainState: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> = _mainState

    private val _clientsResponseState: MutableSharedFlow<ClientsResponseState> = MutableSharedFlow()
    val clientsResponseState: SharedFlow<ClientsResponseState> = _clientsResponseState

    private val clientRemoteDataSource = ClientRemoteDataSource()

    fun getClients(token: String) {
        _mainState.update {
            it.copy(
                loading = true
            )
        }

        viewModelScope.launch {
            val result = clientRemoteDataSource.getClients(token)

            when (result.isSuccess) {
                true -> {
                    _clientsResponseState.emit(
                        ClientsResponseState.Success(
                            clients = result.getOrNull()!!.data
                        )
                    )
                }
                false -> {
                    _clientsResponseState.emit(ClientsResponseState.Error)
                }
            }

            _mainState.update {
                it.copy(
                    loading = false
                )
            }
        }
    }
}