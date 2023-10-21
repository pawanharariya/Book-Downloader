package com.psh.assignment.design

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.psh.assignment.data.Repository
import com.psh.assignment.data.model.Design
import com.psh.assignment.data.model.Section
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DesignViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val designs: LiveData<List<Design>>
        get() = repository.designs

    /**
     * Transforms designs LiveData and segregates them based on their section
     */
    val sections: LiveData<List<Section>> = designs.map {
        designs.value?.groupBy { it.section }?.entries?.map { Section(it.key, it.value) }?.toList()
            ?: listOf()
    }

    init {
        viewModelScope.launch {
            repository.getDesign()
        }
    }
}