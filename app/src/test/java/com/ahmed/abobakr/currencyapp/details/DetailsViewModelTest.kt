package com.ahmed.abobakr.currencyapp.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmed.abobakr.currencyapp.base.NetworkException
import com.ahmed.abobakr.currencyapp.base.UiState
import com.ahmed.abobakr.currencyapp.details.data.ConvertBaseCurrency
import com.ahmed.abobakr.currencyapp.details.data.DetailRepository
import com.ahmed.abobakr.currencyapp.details.viewmodels.DetailsUiState
import com.ahmed.abobakr.currencyapp.details.viewmodels.DetailsViewModel
import com.ahmed.abobakr.currencyapp.utils.MainCoroutineScopeRule
import com.ahmed.abobakr.currencyapp.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    @get:Rule
    var coroutineTestRule = MainCoroutineScopeRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repo: DetailRepository = mock()

    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup(){
        viewModel = DetailsViewModel(repo)
    }

    @Test
    fun getHistoricalDataFromRepo(){
        runBlocking {
            //Arrange
            //Act
            viewModel.getHistoricalData("EUR", "USD")
            //Asset
            verify(repo, times(1)).getHistoricalData("EUR", "USD", "2023-07-24")
            verify(repo, times(1)).getHistoricalData("EUR", "USD", "2023-07-23")
            verify(repo, times(1)).getHistoricalData("EUR", "USD", "2023-07-22")
        }
    }

    @Test
    fun emitHistoricalDataFromRepo(){
        runBlocking {
            //Arrange
            val expectedYesterday = 110.82
            val expectedDayBeforeYesterday = 110.81
            val expected2DaysBeforeYesterday = 110.80
            whenever(repo.getHistoricalData("EUR", "USD", "2023-07-24")).thenReturn(
                flow { emit(expectedYesterday) }
            )
            whenever(repo.getHistoricalData("EUR", "USD", "2023-07-23")).thenReturn(
                flow { emit(expectedDayBeforeYesterday) }
            )
            whenever(repo.getHistoricalData("EUR", "USD", "2023-07-22")).thenReturn(
                flow { emit(expected2DaysBeforeYesterday) }
            )
            //Act
            viewModel.getHistoricalData("EUR", "USD")
            //Assert
            assertEquals(DetailsUiState.HistoricalUiState(listOf(expectedYesterday, expectedDayBeforeYesterday,
                expected2DaysBeforeYesterday)), viewModel.uiState.getValueForTest())
        }
    }

    @Test
    fun emitErrorWhenReceiveErrorFromRepo(){
        runBlocking {
            //Arrange
            val expectedYesterday = 110.82
            val expected2DaysBeforeYesterday = 110.80
            val exception = NetworkException("Please check your Internet connection")
            whenever(repo.getHistoricalData("EUR", "USD", "2023-07-24")).thenReturn(
                flow { emit(expectedYesterday) }
            )
            whenever(repo.getHistoricalData("EUR", "USD", "2023-07-23")).thenReturn(
                flow { throw exception }
            )
            whenever(repo.getHistoricalData("EUR", "USD", "2023-07-22")).thenReturn(
                flow { emit(expected2DaysBeforeYesterday) }
            )
            //Act
            viewModel.getHistoricalData("EUR", "USD")
            //Assert
            assertEquals(UiState.Error(exception.message ?: ""), viewModel.uiState.getValueForTest())
        }
    }

    @Test
    fun convertBaseCurrencyFromRepo(){
        runBlocking {
            //Arrange
            //Act
            viewModel.convertBaseCurrencies("EUR")
            //Assert
            verify(repo, times(1)).convertBaseCurrencies("EUR")
        }
    }

    @Test
    fun getConvertBaseCurrencyFromRepo(){
        runBlocking {
            //Arrange
            val expected = listOf(ConvertBaseCurrency("USD", 1.11))
            whenever(repo.convertBaseCurrencies("EUR")).thenReturn(
                flow { emit(expected) }
            )
            //Act
            viewModel.convertBaseCurrencies("EUR")
            //Assert
            assertEquals(DetailsUiState.BaseCurrenciesUiState(expected), viewModel.uiState.getValueForTest())
        }
    }

    @Test
    fun emitErrorWhenReceiveErrorForConvertBaseCurrencyFromRepo(){
        runBlocking {
            //Arrange
            val exception = NetworkException("Please check your Internet connection")
            whenever(repo.convertBaseCurrencies("EUR")).thenReturn(
                flow { throw exception }
            )
            //Act
            viewModel.convertBaseCurrencies("EUR")
            //Assert
            assertEquals(UiState.Error(exception.message ?: ""), viewModel.uiState.getValueForTest())
        }
    }

}