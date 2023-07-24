package com.ahmed.abobakr.currencyapp.home.data

import javax.inject.Inject

class ConvertCurrencyMapper @Inject constructor(): Function2<LatestCurrencyChangeResponse, String, Double> {

    override fun invoke(p1: LatestCurrencyChangeResponse, p2: String): Double {
        return p1.rates.get(p2).asDouble
    }
}