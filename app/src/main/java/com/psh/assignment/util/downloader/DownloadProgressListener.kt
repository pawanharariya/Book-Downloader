package com.psh.assignment.util.downloader

interface DownloadProgressListener {

    fun onProgressUpdate(progress: Int)

    fun onProgressCancel()

    fun onProgressComplete()
}