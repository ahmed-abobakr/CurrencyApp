package com.ahmed.abobakr.currencyapp.details.data

import com.ahmed.abobakr.currencyapp.home.data.LatestCurrencyChangeResponse
import javax.inject.Inject

class BaseCurrencyMapper @Inject constructor(): Function2<LatestCurrencyChangeResponse, List<String>, List<ConvertBaseCurrency>> {

    override fun invoke(
        p1: LatestCurrencyChangeResponse,
        p2: List<String>
    ): List<ConvertBaseCurrency> {
        val listResult = arrayListOf<ConvertBaseCurrency>()
        for (query in p2){
            listResult.add(ConvertBaseCurrency(query, p1.rates.get(query).asDouble))
        }
        return listResult
    }
}