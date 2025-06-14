package com.abdulgafur.demirci.cryptocurrencies.model

import com.google.gson.annotations.SerializedName

data class Currency(
//    @SerializedName(value = "currency")
    val currency: String,
//    @SerializedName(value = "price")
    val price: String
)