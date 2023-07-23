package com.ahmed.abobakr.currencyapp.base

abstract class UiState {
    object Loading : UiState()

    data class Error(val message: String) : UiState()
}