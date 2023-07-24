package com.ahmed.abobakr.currencyapp.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmed.abobakr.currencyapp.base.NetworkException
import com.ahmed.abobakr.currencyapp.base.UiState
import com.ahmed.abobakr.currencyapp.home.data.ConvertCurrencyResponse
import com.ahmed.abobakr.currencyapp.home.data.HomeRepository
import com.ahmed.abobakr.currencyapp.home.viewmodels.HomeUiState
import com.ahmed.abobakr.currencyapp.home.viewmodels.HomeViewModel
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
class HomeViewModelTest {

    @get:Rule
    var coroutineTestRule = MainCoroutineScopeRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repo: HomeRepository = mock()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp(){
        viewModel = HomeViewModel(repo)
    }

    @Test
    fun convertCurrencyFromRepo(){
        runBlocking {
            //Arrange
            //Act
            viewModel.convertBetweenCurrencies("EGP", "USD", 100)
            //Assert
            verify(repo, times(1)).convertBetweenCurrencies("EGP", "USD", 100)
        }
    }

    @Test
    fun getValueOfConvertedCurrencyFromRepo(){
        runBlocking {
            //Arrange
            val expected = ConvertCurrencyResponse(true, "2023-07-24", 3.25)
            whenever(repo.convertBetweenCurrencies("EGP", "USD", 100)).thenReturn(
                flow { emit(expected) }
            )
            //Act
            viewModel.convertBetweenCurrencies("EGP", "USD", 100)
            //Assert
            assertEquals(HomeUiState.Success(expected), viewModel.uiState.getValueForTest())
        }
    }

    @Test
    fun getErrorFromRepoWhenRepoReturnError(){
        runBlocking {
            //Arrange
            val exception = NetworkException("Please check your Internet connection")
            whenever(repo.convertBetweenCurrencies("EGP", "USD", 100)).thenReturn(
                flow { throw exception }
            )
            //Act
            viewModel.convertBetweenCurrencies("EGP", "USD", 100)
            //Assert
            assertEquals(UiState.Error(exception.message ?: ""), viewModel.uiState.getValueForTest())
        }
    }

}