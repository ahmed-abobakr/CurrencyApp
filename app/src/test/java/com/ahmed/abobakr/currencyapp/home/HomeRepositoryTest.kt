package com.ahmed.abobakr.currencyapp.home

import com.ahmed.abobakr.currencyapp.base.ApiException
import com.ahmed.abobakr.currencyapp.home.data.ConvertCurrencyResponse
import com.ahmed.abobakr.currencyapp.home.data.HomeApi
import com.ahmed.abobakr.currencyapp.home.data.HomeRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HomeRepositoryTest {

    private val api: HomeApi = mock()
    private lateinit var repo: HomeRepository

    @Before
    fun setup(){
        repo = HomeRepository(api)
    }
    @Test
    fun convertCurrencyFromAPI(){
        runBlocking {
            //Arrange
            //Act
            repo.convertBetweenCurrencies("EGP", "USD", 100).first()
            //Assert
            verify(api, times(1)).convertBetweenCurrencies(from = "EGP", to = "USD", amount= 100)
        }
    }

    @Test
    fun emitConvertedCurrencyFromAPI(){
        runBlocking {
            //Arrange
            val expected: ConvertCurrencyResponse = mock()
            whenever(api.convertBetweenCurrencies("EGP", "USD", 100)).thenReturn(expected)
            //Act
            //Assert
            assertEquals(expected, repo.convertBetweenCurrencies("EGP", "USD", 100).first())
        }
    }

    @Test(expected = ApiException::class)
    fun emitErrorWhenConvertCurrencyReceiverErrorFromAPI(){
        runBlocking {
            //Arrange
            val exception = RuntimeException("Error!")
            whenever(api.convertBetweenCurrencies("EGP", "USD", 100)).thenThrow(exception)
            //Act
            repo.convertBetweenCurrencies("EGP", "USD", 100).first()
            //Assert
        }
    }
}