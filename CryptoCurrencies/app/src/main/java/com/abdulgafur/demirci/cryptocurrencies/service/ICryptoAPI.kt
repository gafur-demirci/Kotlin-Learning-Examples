package com.abdulgafur.demirci.cryptocurrencies.service

import com.abdulgafur.demirci.cryptocurrencies.model.Currency
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface ICryptoAPI {

    @GET(value = "atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getData(): Observable<List<Currency>>


//    fun getData(): Call<List<Currency>>

}