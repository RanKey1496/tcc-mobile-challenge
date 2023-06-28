package com.jimbo.jimboapp.ui.main

import com.jimbo.jimboapp.data.response.Client

data class MainState(
    val loading: Boolean = false
)

sealed class ClientsResponseState {
    data class Success(
        val clients: List<Client>
    ) : ClientsResponseState()
    object Error: ClientsResponseState()
}