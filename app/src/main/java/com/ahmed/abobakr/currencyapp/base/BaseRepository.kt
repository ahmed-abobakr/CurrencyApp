package com.ahmed.abobakr.currencyapp.base

import com.google.gson.Gson
import kotlinx.coroutines.TimeoutCancellationException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException

open class BaseRepository {

    protected suspend fun <T> networkHandler(fetch: suspend () -> T): T? {
        try {
            val response = fetch.invoke()
            if ((response as BaseResponse).success)
                return response
            else {
                throw  HttpException(
                    Response.error<Any>(
                        500,
                        response.toString().toResponseBody("application/json".toMediaTypeOrNull())
                    )
                )
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            handleException(throwable)
        }
        return null
    }

    private fun handleException(throwable: Throwable) {
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
            val baseResponse = Gson().fromJson(
                throwable.response()?.errorBody()?.string(),
                BaseResponse::class.java
            )
            val errorMessage = when {
                !baseResponse?.error?.info.equals("null", true) -> baseResponse?.error?.info
                !baseResponse?.error?.type.equals("null", true) -> baseResponse?.error?.type
                else -> ""
            }
            return errorMessage ?: ""
        } catch (exception: Exception) {
            exception.printStackTrace()
            UNKNOWN_ERROR
        }
    }
}