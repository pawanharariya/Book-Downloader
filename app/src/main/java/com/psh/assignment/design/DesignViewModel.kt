package com.psh.assignment.design

import android.app.Application
import android.app.DownloadManager
import android.webkit.MimeTypeMap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.psh.assignment.Event
import com.psh.assignment.R
import com.psh.assignment.data.Repository
import com.psh.assignment.data.model.Design
import com.psh.assignment.data.model.Section
import com.psh.assignment.util.downloader.DownloadProgressLiveData
import com.psh.assignment.util.downloader.FileDownloader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DesignViewModel @Inject constructor(
    private val repository: Repository,
    private val application: Application,
    private val fileDownloader: FileDownloader
) : AndroidViewModel(application) {

    private val designs: LiveData<List<Design>>
        get() = repository.designs
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    /**
     * Transforms designs LiveData and segregates them based on their section
     */
    var sections: MutableLiveData<List<Section>> = designs.map {
        designs.value?.groupBy { it.section }?.entries?.map { Section(it.key, it.value) }?.toList()
            ?: listOf()
    } as MutableLiveData<List<Section>>

    /**
     * Hashmap to store downloadProgressObservers against design id
     */
    val progressLiveDataMap = MutableLiveData<HashMap<String, DownloadProgressLiveData>>()

    init {
        viewModelScope.launch {
            repository.getDesign()
        }

        progressLiveDataMap.value = HashMap()
    }

    fun downloadFileAttached(design: Design) {
        if (isDownloadingAlready(design.id)) return
        val url = design.file
        val fileName = url.substring(url.lastIndexOf('/') + 1)
        val mimeType = getMimeFromFileName(fileName)
        if (mimeType == null) {
            showSnackbarMessage(R.string.mime_type_invalid)
            return
        }
        val requestId = fileDownloader.downloadFile(url, mimeType, fileName)
        attachProgressObserver(design.id, requestId)
    }

    // prevents downloads in-case of multiple clicks
    private fun isDownloadingAlready(id: String): Boolean {
        return progressLiveDataMap.value?.get(id)?.value?.status == DownloadManager.STATUS_RUNNING
    }

    private fun attachProgressObserver(designId: String, requestId: Long) {
        val hashMap = progressLiveDataMap.value
        hashMap?.set(designId, DownloadProgressLiveData(application.applicationContext, requestId))
        progressLiveDataMap.value = progressLiveDataMap.value
    }

    fun cancelDownload(designId: String) {
        val requestId = progressLiveDataMap.value?.get(designId)?.requestId
        requestId?.let {
            fileDownloader.cancelDownload(requestId)
        }
    }

    private fun getMimeFromFileName(fileName: String): String? {
        val map = MimeTypeMap.getSingleton()
        val ext = MimeTypeMap.getFileExtensionFromUrl(fileName)
        return map.getMimeTypeFromExtension(ext)
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }
}


