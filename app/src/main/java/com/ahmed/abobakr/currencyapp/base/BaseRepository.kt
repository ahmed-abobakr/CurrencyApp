package com.ahmed.abobakr.currencyapp.base

import com.google.gson.Gson
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.net.SocketTimeoutException

open class BaseRepository {

    protected fun handleException(throwable: Throwable) {
        when (throwable) {
            is TimeoutCancellationException -> {
                throw NetworkException("The connection has timed out.")
            }
            is SocketTimeoutException -> {
                throw NetworkException("Server error, please try again later.")
            }
            is HttpException -> {
                handleNetworkException(throwable, "Something went wrong!")
            }
            else -> {
                throw ApiException("Something went wrong!")
            }
        }
    }

    private fun handleNetworkException(throwable: HttpException, UNKNOWN_ERROR: String) {
        when (throwable.code()) {
            401 -> throw AuthException(getErrorFrom(throwable, UNKNOWN_ERROR))
            else -> throw ApiException(getErrorFrom(throwable, UNKNOWN_ERROR))
        }
    }

    private fun getErrorFrom(throwable: HttpException, UNKNOWN_ERROR: String): String {
        return try {
            Gson().fromJson(
                throwable.response()?.errorBody()?.string(),
                BaseResponse::class.java
            ).error?.info ?: ""
        } catch (exception: Exception) {
            UNKNOWN_ERROR
        }
    }
}