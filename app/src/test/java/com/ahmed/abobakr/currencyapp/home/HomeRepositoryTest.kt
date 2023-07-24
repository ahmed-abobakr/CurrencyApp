package com.ahmed.abobakr.currencyapp.home

import com.ahmed.abobakr.currencyapp.base.ApiException
import com.ahmed.abobakr.currencyapp.home.data.ConvertCurrencyMapper
import com.ahmed.abobakr.currencyapp.home.data.HomeApi
import com.ahmed.abobakr.currencyapp.home.data.HomeRepository
import com.ahmed.abobakr.currencyapp.home.data.LatestCurrencyChangeResponse
import com.google.gson.JsonObject
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HomeRepositoryTest {

    private val api: HomeApi = mock()
    private val mapper: ConvertCurrencyMapper = mock()
    private lateinit var repo: HomeRepository

    @Before
    fun setup(){
        repo = HomeRepository(api, mapper)
    }
    @Test
    fun convertCurrencyFromAPI(){
        runBlocking {
            //Arrange
            val expected = 120.0
            val rateJsonObject = JsonObject()
            rateJsonObject.addProperty("USD", expected)
            val latestCurrencyChangeResponse = LatestCurrencyChangeResponse(true, "EUR", rateJsonObject)
            whenever(api.convertBetweenCurrencies("EUR", "USD")).thenReturn(latestCurrencyChangeResponse)
            //Act
            repo.convertBetweenCurrencies("EUR", "USD").first()
            //Assert
            verify(api, times(1)).convertBetweenCurrencies(from = "EUR", to = "USD")
        }
    }

    @Test
    fun emitConvertedCurrencyFromAPI(){
        runBlocking {
            //Arrange
            val expected = 120.0
            val rateJsonObject = JsonObject()
            rateJsonObject.addProperty("USD", expected)
            val latestCurrencyChangeResponse = LatestCurrencyChangeResponse(true, "EUR", rateJsonObject)
            whenever(api.convertBetweenCurrencies("EUR", "USD")).thenReturn(latestCurrencyChangeResponse)
            whenever(mapper(latestCurrencyChangeResponse, "USD")).thenReturn(expected)
            //Act
            //Assert
            assert(expected == repo.convertBetweenCurrencies("EUR", "USD").first())
        }
    }

    @Test(expected = ApiException::class)
    fun emitErrorWhenConvertCurrencyReceiverErrorFromAPI(){
        runBlocking {
            //Arrange
            val exception = RuntimeException("Error!")
            whenever(api.convertBetweenCurrencies("EGP", "USD")).thenThrow(exception)
            //Act
            repo.convertBetweenCurrencies("EGP", "USD").first()
            //Assert
        }
    }
}