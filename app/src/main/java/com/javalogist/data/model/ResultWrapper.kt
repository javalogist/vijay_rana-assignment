package com.javalogist.data.model

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    object Loading : ResultWrapper<Nothing>()
    data class Error(val errorMessage: String? = null) : ResultWrapper<Nothing>()
}