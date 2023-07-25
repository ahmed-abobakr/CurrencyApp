package com.ahmed.abobakr.currencyapp.details.data

import com.ahmed.abobakr.currencyapp.BuildConfig
import com.ahmed.abobakr.currencyapp.home.data.LatestCurrencyChangeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailsApi {

    @GET("{date}")
    suspend fun getHistoricalData(@Path("date") date: String, @Query("base") from: String, @Query("symbols") to: String,
            @Query("access_key") key: String = BuildConfig.API_KEY): LatestCurrencyChangeResponse

    @GET("latest")
    suspend fun convertBaseCurrencies(@Query("base") from: String, @Query("symbols") symbols: String,
                              @Query("access_key") key: String = BuildConfig.API_KEY): LatestCurrencyChangeResponse
}