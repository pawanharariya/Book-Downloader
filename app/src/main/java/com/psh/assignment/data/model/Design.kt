package com.psh.assignment.data.model

import com.psh.assignment.util.downloader.DownloadItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Design(
    val id: String,
    var name: String,
    val type: DesignType,
    val section: SectionType,
    val file: String = "",
    val version: Int = 1,
    @Json(name = "uploaded_at")
    val uploadedAt: String,
    @Transient
    var downloadItem: DownloadItem? = null
)
