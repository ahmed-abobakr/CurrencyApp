package com.ahmed.abobakr.currencyapp.home.data

import com.ahmed.abobakr.currencyapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("convert")
    suspend fun convertBetweenCurrencies(@Query("from") from: String, @Query("to")to: String, @Query("amount")amount: Int,
                                 @Query("access_key") key: String = BuildConfig.API_KEY): ConvertCurrencyResponse
}