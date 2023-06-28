package com.jimbo.jimboapp.data.response

import com.google.gson.annotations.SerializedName

data class Client(
    @SerializedName("identification") val identification: String,
    @SerializedName("identificationType") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: String
)