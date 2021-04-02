package com.daya.notification_prototype.data

sealed class Resource<out T: Any> {
    data class Success<out T: Any>(val data: T): Resource<T>()
    data class Error(val exception: Throwable): Resource<Nothing>()
    data class Loading<out T>(var progress : String?): Resource<Nothing>()
}