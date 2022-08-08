package com.tuangiao.monolith.common

typealias SimpleResource = DataResult<Unit>

sealed class DataResult<out T> {
    class Success<T>(val data: T) : DataResult<T>()
    data class Error<T>(val exception: Exception) : DataResult<T>()
    object Loading : DataResult<Nothing>()
}


