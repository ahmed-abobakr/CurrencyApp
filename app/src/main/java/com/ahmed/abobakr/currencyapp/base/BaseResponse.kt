package com.ahmed.abobakr.currencyapp.base

open class BaseResponse(val success: Boolean, val error: Error? = null){
    override fun toString(): String {
        return "{\"success\":$success,\"error\":{\"code\":${error?.code},\"info\":\"${error?.info}\",\"type\":${error?.type}\"}}"
    }
}

data class Error(val code: Int, val info: String?, val type: String?)