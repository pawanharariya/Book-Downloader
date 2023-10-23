package com.psh.assignment.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.psh.assignment.data.model.Design
import com.psh.assignment.data.remote.RemoteAPI
import javax.inject.Inject

class Repository @Inject constructor(private val remoteAPI: RemoteAPI) {

    private val _designs = MutableLiveData<List<Design>>()
    val designs: LiveData<List<Design>>
        get() = _designs

    suspend fun getDesign() {
        val result = remoteAPI.getDesigns()
        if (result.success) {
            _designs.postValue(result.data)
        }
    }
}