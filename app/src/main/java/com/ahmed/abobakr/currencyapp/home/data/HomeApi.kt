package com.ahmed.abobakr.currencyapp.home.data

import com.ahmed.abobakr.currencyapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("latest")
    suspend fun convertBetweenCurrencies(@Query("base") from: String, @Query("symbols")to: String,
                                 @Query("access_key") key: String = BuildConfig.API_KEY): LatestCurrencyChangeResponse
}