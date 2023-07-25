package com.ahmed.abobakr.currencyapp.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmed.abobakr.currencyapp.base.ApiException
import com.ahmed.abobakr.currencyapp.details.data.BaseCurrencyMapper
import com.ahmed.abobakr.currencyapp.details.data.ConvertBaseCurrency
import com.ahmed.abobakr.currencyapp.details.data.DetailRepository
import com.ahmed.abobakr.currencyapp.details.data.DetailsApi
import com.ahmed.abobakr.currencyapp.home.data.ConvertCurrencyMapper
import com.ahmed.abobakr.currencyapp.home.data.LatestCurrencyChangeResponse
import com.ahmed.abobakr.currencyapp.utils.MainCoroutineScopeRule
import com.google.gson.JsonObject
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsRepoTest {

    @get:Rule
    var coroutineTestRule = MainCoroutineScopeRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val api: DetailsApi = mock()
    private val mapper: ConvertCurrencyMapper = mock()
    private val baseCurrencyMapper: BaseCurrencyMapper = mock()

    private lateinit var repo: DetailRepository

    @Before
    fun setup(){
        repo = DetailRepository(api, mapper, baseCurrencyMapper)
    }

    @Test
    fun getHistoricalDataFromAPI(){
        runBlocking {
            //Arrange
            val expected = 120.0
            val rateJsonObject = JsonObject()
            rateJsonObject.addProperty("USD", expected)
            val latestCurrencyChangeResponse = LatestCurrencyChangeResponse(true, "EUR", rateJsonObject)
            whenever(api.getHistoricalData("2023-07-24", "EUR", "USD")).thenReturn(latestCurrencyChangeResponse)
            //Act
            repo.getHistoricalData("EUR", "USD", "2023-07-24").first()
            //Assert
            verify(api, times(1)).getHistoricalData("2023-07-24", "EUR", "USD", )
        }
    }

    @Test
    fun emitHistoricalDataFromAPI(){
        runBlocking {
            //Arrange
            val expected = 120.0
            val rateJsonObject = JsonObject()
            rateJsonObject.addProperty("USD", expected)
            val latestCurrencyChangeResponse = LatestCurrencyChangeResponse(true, "EUR", rateJsonObject)
            whenever(api.getHistoricalData("2023-07-24", "EUR", "USD")).thenReturn(latestCurrencyChangeResponse)
            whenever(mapper.invoke(latestCurrencyChangeResponse, "USD")).thenReturn(expected)
            //Act
            //Assert
            assert(expected == repo.getHistoricalData("EUR", "USD", "2023-07-24").first())
        }
    }

    @Test(expected = ApiException::class)
    fun emitErrorWhenReceiveErrorWhenGetHistoricalDataFromAPI(){
        runBlocking {
            //Arrange
            val exception = RuntimeException("Error!")
            whenever(api.getHistoricalData("2023-07-24", "EUR", "USD")).thenThrow(exception)
            //Act
            repo.getHistoricalData("EUR", "USD", "2023-07-24").first()
            //Assert
        }
    }

    @Test
    fun getConvertBaseCurrenciesFromAPI(){
        runBlocking {
            //Arrange
            val expectedUSD = 120.0
            val expectedGBP = 115.0
            val rateJsonObject = JsonObject()
            rateJsonObject.addProperty("USD", expectedUSD)
            rateJsonObject.addProperty("GBP", expectedGBP)
            val latestCurrencyChangeResponse = LatestCurrencyChangeResponse(true, "EUR", rateJsonObject)
            whenever(api.convertBaseCurrencies("EUR", "USD,AUD,CAD,PLN,MXN,GBP,JPY,SAR,KWD,QAR")).thenReturn(latestCurrencyChangeResponse)
            //Act
            repo.convertBaseCurrencies("EUR").first()
            //Assert
            verify(api, times(1)).convertBaseCurrencies("EUR", "USD,AUD,CAD,PLN,MXN,GBP,JPY,SAR,KWD,QAR")
        }
    }

    @Test
    fun emitConvertBaseCurrenciesFromAPI(){
        runBlocking {
            //Arrange
            val expectedUSD = 120.0
            val expectedGBP = 115.0
            val expectBaseCurrencyUSD = ConvertBaseCurrency("USD", expectedUSD)
            val expectBaseCurrencyGBP = ConvertBaseCurrency("GBP", expectedGBP)
            val rateJsonObject = JsonObject()
            rateJsonObject.addProperty("USD", expectedUSD)
            rateJsonObject.addProperty("GBP", expectedGBP)
            val latestCurrencyChangeResponse = LatestCurrencyChangeResponse(true, "EUR", rateJsonObject)
            whenever(api.convertBaseCurrencies("EUR", "USD,AUD,CAD,PLN,MXN,GBP,JPY,SAR,KWD,QAR")).thenReturn(latestCurrencyChangeResponse)
            whenever(baseCurrencyMapper.invoke(latestCurrencyChangeResponse, listOf("USD","AUD","CAD","PLN","MXN","GBP","JPY","SAR","KWD","QAR")))
                .thenReturn(listOf(expectBaseCurrencyUSD, expectBaseCurrencyGBP))
            //Act
            //Assert
            assertEquals(listOf(expectBaseCurrencyUSD, expectBaseCurrencyGBP), repo.convertBaseCurrencies("EUR").first())
        }
    }

    @Test(expected = ApiException::class)
    fun emitErrorWhenReceiveErrorWhenConvertBaseCurrencyFromAPI(){
        runBlocking {
            //Arrange
            val exception = RuntimeException("Error!")
            whenever(api.convertBaseCurrencies("EUR", "USD,AUD,CAD,PLN,MXN,GBP,JPY,SAR,KWD,QAR")).thenThrow(exception)
            //Act
            repo.convertBaseCurrencies("EUR").first()
            //Assert
        }
    }
}