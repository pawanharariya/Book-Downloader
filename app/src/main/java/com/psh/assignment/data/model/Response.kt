package com.psh.assignment.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    val code: Int,
    val message: String,
    val success: Boolean,
    val data: List<Design>
)

enum class ResponseCode {
    @Json(name = "200")
    SUCCESS,

    @Json(name = "404")
    ERROR
}