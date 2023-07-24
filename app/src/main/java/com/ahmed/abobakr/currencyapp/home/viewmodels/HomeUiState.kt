package com.ahmed.abobakr.currencyapp.home.viewmodels

import com.ahmed.abobakr.currencyapp.base.UiState

sealed class HomeUiState: UiState() {

    data class Success(val result: String): UiState()
}