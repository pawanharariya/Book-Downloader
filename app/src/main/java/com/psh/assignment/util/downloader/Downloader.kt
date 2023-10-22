package com.psh.assignment.util.downloader

interface Downloader {
    fun downloadFile(url: String, mimeType: String, fileName: String): Long

    fun cancelDownload(requestId: Long): Int
}