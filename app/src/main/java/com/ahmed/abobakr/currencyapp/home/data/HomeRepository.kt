package com.ahmed.abobakr.currencyapp.home.data

import com.ahmed.abobakr.currencyapp.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: HomeApi, private val mapper: ConvertCurrencyMapper): BaseRepository() {

    suspend fun convertBetweenCurrencies(from: String, to: String): Flow<Double>{
        return flow {
            //emit(mapper.invoke(api.convertBetweenCurrencies(from, to), from))
            val response = networkHandler<LatestCurrencyChangeResponse> { api.convertBetweenCurrencies(from, to) }
                emit(mapper.invoke(response as LatestCurrencyChangeResponse, to))
        }
    }
}