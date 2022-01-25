package com.example.moviecatalogue.data

/**
 * Created by Seline on 24/01/2022 18:48
 */
sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
