package com.jimbo.jimboapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetworkProvider {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://64.227.8.154:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi: AuthApi = retrofit.create(AuthApi::class.java)
    val clientApi: ClientApi = retrofit.create(ClientApi::class.java)
}
