package com.psh.assignment.design

import android.app.Application
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
    private val repository: Repository, private val application: Application
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

    init {
        viewModelScope.launch {
            repository.getDesign()
        }
    }

    fun downloadFileAttached(design: Design) {
        val url = design.file
        val fileName = url.substring(url.lastIndexOf('/') + 1)
        val mimeType = getMimeFromFileName(fileName)
        if (mimeType == null) {
            showSnackbarMessage(R.string.mime_type_invalid)
            return
        }
        val requestId = FileDownloader(application.applicationContext).downloadFile(
            url, mimeType, fileName
        )
        attachProgressObserver(requestId)
    }

    private fun attachProgressObserver(requestId: Long) {
        val progress = DownloadProgressLiveData(application.applicationContext, requestId)
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



