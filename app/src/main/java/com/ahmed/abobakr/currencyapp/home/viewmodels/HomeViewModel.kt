package com.ahmed.abobakr.currencyapp.home.viewmodels

import androidx.lifecycle.viewModelScope
import com.ahmed.abobakr.currencyapp.base.BaseViewModel
import com.ahmed.abobakr.currencyapp.base.UiState
import com.ahmed.abobakr.currencyapp.home.data.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: HomeRepository): BaseViewModel() {

    fun convertBetweenCurrencies(from: String, to: String, amount: Int){
        uiState.value = UiState.Loading
        viewModelScope.launch {
            repo.convertBetweenCurrencies(from, to, amount)
                .catch {
                    uiState.value = UiState.Error(it.message ?: "")
                }
                .collect {
                    uiState.value = HomeUiState.Success(it)
                }
        }
    }
}