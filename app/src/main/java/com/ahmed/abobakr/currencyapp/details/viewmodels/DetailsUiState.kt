package com.ahmed.abobakr.currencyapp.details.viewmodels

import com.ahmed.abobakr.currencyapp.base.UiState
import com.ahmed.abobakr.currencyapp.details.data.ConvertBaseCurrency

sealed class DetailsUiState: UiState() {

    data class HistoricalUiState(val result: List<Double>): UiState()

    data class BaseCurrenciesUiState(val result: List<ConvertBaseCurrency>): UiState()
}