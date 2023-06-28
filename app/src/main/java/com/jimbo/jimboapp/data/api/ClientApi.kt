package com.jimbo.jimboapp.data.api

import com.jimbo.jimboapp.data.response.ClientsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ClientApi {

    @GET("client")
    suspend fun getClients(
        @Header("Authorization") authorization: String
    ): Response<ClientsResponse>
}