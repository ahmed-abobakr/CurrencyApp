package com.ahmed.abobakr.currencyapp.base

import com.ahmed.abobakr.currencyapp.details.data.BaseCurrencyMapper
import com.ahmed.abobakr.currencyapp.details.data.DetailsApi
import com.ahmed.abobakr.currencyapp.home.data.ConvertCurrencyMapper
import com.ahmed.abobakr.currencyapp.home.data.HomeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder().addInterceptor(logger).build()

    @Provides
    fun provideConvertCurrenctMapper() = ConvertCurrencyMapper()

    @Provides
    fun provideBaseCurrencyConvertMapper() = BaseCurrencyMapper()

    @Provides
    fun provideHomeAPI(retrofit: Retrofit): HomeApi = retrofit.create(HomeApi::class.java)

    @Provides
    fun provideDetailsAPI(retrofit: Retrofit): DetailsApi = retrofit.create(DetailsApi::class.java)

    @Provides
    fun retrofit() = Retrofit.Builder()
        .baseUrl("http://data.fixer.io/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}