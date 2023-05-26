package com.oompa.loompa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oompa.loompa.model.OompaLoompa
import com.oompa.loompa.paging.data.OompaLoompaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class OompaLoompaViewModel @Inject constructor(
    private val repository: OompaLoompaRepository,
): ViewModel() {
    fun getOompaLoompas(): Flow<PagingData<OompaLoompa>> = repository.getOompaLoompas().cachedIn(viewModelScope)
}