package com.psh.assignment.data

import com.psh.assignment.data.Result.Success

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : Result<T>(data)

    class Error<T>(message: String?) : Result<T>(message = message)

}

val Result<*>.succeeded
    get() = this is Success && data != null