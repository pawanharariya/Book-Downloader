package com.psh.assignment.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Design(
    val id: String,
    val name: String,
    val type: DesignType,
    val section: SectionType,
    val file: String = "",
    val version: Int = 1,
    @Json(name = "uploaded_at")
    val uploadedAt: String
)
