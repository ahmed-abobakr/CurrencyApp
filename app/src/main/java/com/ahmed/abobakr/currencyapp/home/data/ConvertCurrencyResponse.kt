package com.ahmed.abobakr.currencyapp.home.data

import com.ahmed.abobakr.currencyapp.base.BaseResponse

class ConvertCurrencyResponse(success: Boolean, val date: String, val result: Double): BaseResponse(success)