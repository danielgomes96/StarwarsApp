package com.daniel.domain.entity

sealed class ResultWrapper<out T> {
    class InitialState<T> : ResultWrapper<T>()
    object Loading : ResultWrapper<Nothing>()
    object Empty : ResultWrapper<Nothing>()
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val errorMessage: String? = null) : ResultWrapper<Nothing>()
    object NetworkError : ResultWrapper<Nothing>()
}

