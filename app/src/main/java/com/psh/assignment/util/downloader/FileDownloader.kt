package com.psh.assignment.util.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import javax.inject.Inject

class FileDownloader @Inject constructor(private val downloadManager: DownloadManager) :
    Downloader {
    override fun downloadFile(url: String, mimeType: String, fileName: String): Long {
        val downloadRequest = DownloadManager.Request(url.toUri())
            .setMimeType(mimeType)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(fileName)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                fileName
            )
        return downloadManager.enqueue(downloadRequest)
    }

    override fun cancelDownload(requestId: Long): Int {
        return downloadManager.remove(requestId)
    }
}