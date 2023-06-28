package com.jimbo.jimboapp.data.response

import com.google.gson.annotations.SerializedName

data class ClientsResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: List<Client>
)