package com.ahmed.abobakr.currencyapp.details.data

import com.ahmed.abobakr.currencyapp.base.BaseRepository
import com.ahmed.abobakr.currencyapp.home.data.ConvertCurrencyMapper
import com.ahmed.abobakr.currencyapp.home.data.LatestCurrencyChangeResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailRepository @Inject constructor(private val api: DetailsApi, private val mapper: ConvertCurrencyMapper,
        private val baseCurrencyMapper: BaseCurrencyMapper): BaseRepository() {

    private val listBaseCurrencies = listOf("EUR","USD","AUD","CAD","PLN","MXN","GBP","JPY","SAR","KWD","QAR")

    suspend fun getHistoricalData(from: String, to: String, date: String): Flow<Double>{
        return flow {
            val response = networkHandler<LatestCurrencyChangeResponse> { api.getHistoricalData(date, from, to) }
            emit(mapper.invoke((response as LatestCurrencyChangeResponse), to))
        }
    }

    suspend fun convertBaseCurrencies(from: String): Flow<List<ConvertBaseCurrency>> {
        return flow {
            val symbols = listBaseCurrencies.filter { it != from }
            var symbolsStr = ""
            symbols.forEach { symbolsStr += "$it," }
            val response = networkHandler { api.convertBaseCurrencies(from, symbolsStr.substring(0, symbolsStr.length - 1)) }
            emit(baseCurrencyMapper.invoke((response as LatestCurrencyChangeResponse), symbols))
        }
    }
}