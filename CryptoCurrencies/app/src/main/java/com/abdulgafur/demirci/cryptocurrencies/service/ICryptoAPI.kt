package com.abdulgafur.demirci.cryptocurrencies.service

import com.abdulgafur.demirci.cryptocurrencies.model.Currency
import retrofit2.Response
import retrofit2.http.GET

interface ICryptoAPI {

    @GET(value = "atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    suspend fun getData(): Response<List<Currency>>

}