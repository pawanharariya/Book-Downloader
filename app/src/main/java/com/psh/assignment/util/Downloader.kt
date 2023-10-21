package com.psh.assignment.util

interface Downloader {
    fun downloadFile(url: String, mimeType: String, fileName: String): Long
}