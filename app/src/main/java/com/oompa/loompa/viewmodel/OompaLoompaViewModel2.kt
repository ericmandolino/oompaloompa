package com.oompa.loompa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oompa.loompa.database.OompaLoompaDatabase
import com.oompa.loompa.model.OompaLoompa
import com.oompa.loompa.paging.data.OompaLoompasRemoteMediator
import com.oompa.loompa.service.OompaLoompaApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val PAGE_SIZE = 20

@HiltViewModel
class OompaLoompaViewModel2 @Inject constructor(
    private val oompaLoompaApiService: OompaLoompaApiService,
    private val oompaLoompaDatabase: OompaLoompaDatabase,
): ViewModel() {
    @OptIn(ExperimentalPagingApi::class)
    fun getOompaLoompas(): Flow<PagingData<OompaLoompa>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            pagingSourceFactory = {
                oompaLoompaDatabase.getOompaLoompasDao().getOompaLoompasPagingSource()
            },
            remoteMediator = OompaLoompasRemoteMediator(
                oompaLoompaApiService,
                oompaLoompaDatabase,
            )
        ).flow.cachedIn(viewModelScope)
}