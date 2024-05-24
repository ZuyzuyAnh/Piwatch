package com.example.piwatch.util

import java.lang.Exception

sealed class Resource<T>(val result: T? = null, val message: String? = null){
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
    class Loading<T>(): Resource<T>(null)
}