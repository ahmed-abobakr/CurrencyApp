package com.ahmed.abobakr.currencyapp.home.data

import com.ahmed.abobakr.currencyapp.base.BaseResponse
import com.google.gson.JsonObject

class LatestCurrencyChangeResponse(success: Boolean, val base: String, val rates: JsonObject): BaseResponse(success)

