package com.jimbo.jimboapp.data.datasource.client

import com.jimbo.jimboapp.data.api.AuthBody
import com.jimbo.jimboapp.data.api.ClientApi
import com.jimbo.jimboapp.data.api.NetworkProvider
import com.jimbo.jimboapp.data.response.ClientsResponse

class ClientRemoteDataSource constructor(
    private val clientApi: ClientApi = NetworkProvider.clientApi
) {

    suspend fun getClients(token: String): Result<ClientsResponse> {
        return try {
            val response = clientApi.getClients(
                "Bearer $token"
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