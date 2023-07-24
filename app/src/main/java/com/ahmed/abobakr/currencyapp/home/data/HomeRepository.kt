package com.ahmed.abobakr.currencyapp.home.data

import com.ahmed.abobakr.currencyapp.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(): BaseRepository() {

    suspend fun convertBetweenCurrencies(from: String, to: String, amount: Int): Flow<ConvertCurrencyResponse>{
        return flow {
            emit(ConvertCurrencyResponse(true, "", 100.0))
        }
    }
}