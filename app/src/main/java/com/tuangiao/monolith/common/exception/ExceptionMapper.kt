package com.tuangiao.monolith.common.exception

import android.util.Log
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import javax.inject.Inject

class ExceptionMapper @Inject constructor() {
    fun mapNetworkError(failure: Exception): NetworkException {
        return when (failure) {
            is SocketTimeoutException -> NetworkException.Network
            is HttpException -> mapApiError(failure.code())
            else -> NetworkException.UnknownError
        }
    }

    private fun mapApiError(statusCode: Int): NetworkException {
        Log.e("Exception is----", statusCode.toString())
        return when (statusCode) {
            // 400
            HttpURLConnection.HTTP_BAD_REQUEST -> NetworkException.BadRequest
            // 401
            HttpURLConnection.HTTP_UNAUTHORIZED -> NetworkException.NotAuthorized
            // 404
            HttpURLConnection.HTTP_NOT_FOUND -> NetworkException.NotFound
            // 500
            HttpURLConnection.HTTP_INTERNAL_ERROR -> NetworkException.ServiceNotWorking
            // 503
            HttpURLConnection.HTTP_UNAVAILABLE -> NetworkException.ServiceUnavailable
            else -> NetworkException.Unknown
        }
    }
}