package com.ahmed.abobakr.currencyapp.home.viewmodels

import com.ahmed.abobakr.currencyapp.base.UiState
import com.ahmed.abobakr.currencyapp.home.data.ConvertCurrencyResponse

sealed class HomeUiState: UiState() {

    data class Success(val result: ConvertCurrencyResponse): UiState()
}