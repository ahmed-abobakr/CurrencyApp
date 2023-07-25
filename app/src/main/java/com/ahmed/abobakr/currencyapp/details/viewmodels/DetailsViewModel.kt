package com.ahmed.abobakr.currencyapp.details.viewmodels

import androidx.lifecycle.viewModelScope
import com.ahmed.abobakr.currencyapp.base.BaseViewModel
import com.ahmed.abobakr.currencyapp.base.UiState
import com.ahmed.abobakr.currencyapp.details.data.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: DetailRepository): BaseViewModel() {

    fun getHistoricalData(from: String, to: String){
        uiState.value = UiState.Loading
        val yesterdayDataDeferred = viewModelScope.async { repository.getHistoricalData(from, to, getYesterdayDate(Calendar.getInstance())) }
        val dayBeforeYesterdayDataDeferred = viewModelScope.async { repository.getHistoricalData(from, to, getDayBeforeYesterdayDate(Calendar.getInstance())) }
        val twoDaysBeforeYesterdayDataDeferred = viewModelScope.async { repository.getHistoricalData(from, to, get2DaysBeforeYesterdayDate(Calendar.getInstance())) }
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            uiState.value = UiState.Error(exception.message ?: "")
        }
        viewModelScope.launch(exceptionHandler) {
            val yesterdayData = yesterdayDataDeferred.await()
            val dayBeforeYesterdayData = dayBeforeYesterdayDataDeferred.await()
            val twoDaysBeforeYesterdayData = twoDaysBeforeYesterdayDataDeferred.await()
            uiState.value = DetailsUiState.HistoricalUiState(listOf(yesterdayData.first(), dayBeforeYesterdayData.first(),
                    twoDaysBeforeYesterdayData.first()))
        }

    }

    fun convertBaseCurrencies(from: String){
        viewModelScope.launch {
            repository.convertBaseCurrencies(from)
                .catch {
                    uiState.value = UiState.Error(it.message ?: "")
                }
                .collect{
                uiState.value = DetailsUiState.BaseCurrenciesUiState(it)
            }
        }
    }

    private fun getYesterdayDate(calendar: Calendar): String{
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1)
        val month = if(calendar.get(Calendar.MONTH) + 1 < 10)
                "0${calendar.get(Calendar.MONTH) + 1}" else "${calendar.get(Calendar.MONTH) + 1}"
        val day = if(calendar.get(Calendar.DAY_OF_MONTH) < 10)
            "0${calendar.get(Calendar.DAY_OF_MONTH)}" else "${calendar.get(Calendar.DAY_OF_MONTH)}"
        return "${calendar.get(Calendar.YEAR)}-$month-$day"
    }

    private fun getDayBeforeYesterdayDate(calendar: Calendar): String{
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 2)
        val month = if(calendar.get(Calendar.MONTH) + 1 < 10)
            "0${calendar.get(Calendar.MONTH) + 1}" else "${calendar.get(Calendar.MONTH) + 1}"
        val day = if(calendar.get(Calendar.DAY_OF_MONTH) < 10)
            "0${calendar.get(Calendar.DAY_OF_MONTH)}" else "${calendar.get(Calendar.DAY_OF_MONTH)}"
        return "${calendar.get(Calendar.YEAR)}-$month-$day"
    }

    private fun get2DaysBeforeYesterdayDate(calendar: Calendar): String{
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 3)
        val month = if(calendar.get(Calendar.MONTH) + 1 < 10)
            "0${calendar.get(Calendar.MONTH) + 1}" else "${calendar.get(Calendar.MONTH) + 1}"
        val day = if(calendar.get(Calendar.DAY_OF_MONTH) < 10)
            "0${calendar.get(Calendar.DAY_OF_MONTH)}" else "${calendar.get(Calendar.DAY_OF_MONTH)}"
        return "${calendar.get(Calendar.YEAR)}-$month-$day"
    }
}