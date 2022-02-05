package com.shahad.app.my_school.util

sealed class State<out T> {

    data class Success<T>(var data: T?): State<T>()
    data class Error(val message: String): State<Nothing>()
    object Loading: State<Nothing>()
    object UnAuthorization: State<Nothing>()

    fun toData(): T? = if (this is Success) data else null
}