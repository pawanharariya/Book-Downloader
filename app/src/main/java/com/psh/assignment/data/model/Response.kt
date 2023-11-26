package com.psh.assignment.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    val code: Int,
    val message: String,
    val success: Boolean,
    val data: List<Book>,
    val errors: String?
)