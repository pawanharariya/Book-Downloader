package com.psh.assignment.util.downloader

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DownloadProgressLiveData(private val context: Context, private val requestId: Long) :
    LiveData<DownloadItem>(), CoroutineScope {

    private val downloadManager by lazy {
        context.getSystemService(DownloadManager::class.java) as DownloadManager
    }

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    @SuppressLint("Range")
    override fun onActive() {
        super.onActive()
        launch {
            while (isActive) {
                val query = DownloadManager.Query().setFilterById(requestId)
                val cursor = downloadManager.query(query)
                if (cursor.moveToFirst()) {
                    val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                    when (status) {
                        DownloadManager.STATUS_SUCCESSFUL,
                        DownloadManager.STATUS_PENDING,
                        DownloadManager.STATUS_FAILED,
                        DownloadManager.STATUS_PAUSED -> postValue(DownloadItem(status = status))

                        else -> {
                            val bytesDownloadedSoFar =
                                cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                            val totalSizeBytes =
                                cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                            postValue(
                                DownloadItem(
                                    bytesDownloadedSoFar.toLong(), totalSizeBytes.toLong(), status
                                )
                            )
                        }
                    }
                    if (status == DownloadManager.STATUS_SUCCESSFUL || status == DownloadManager.STATUS_FAILED) cancel()
                } else {
                    postValue(DownloadItem(status = DownloadManager.STATUS_FAILED))
                    cancel()
                }
                cursor.close()
                delay(300)
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        job.cancel()
    }

}

data class DownloadItem(
    val bytesDownloadedSoFar: Long = 0, val totalSizeBytes: Long = -1, val status: Int
)
