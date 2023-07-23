package com.ahmed.abobakr.currencyapp.base

open class BaseResponse(val success: Boolean, val error: Error? = null)

data class Error(val code: Int, val info: String)