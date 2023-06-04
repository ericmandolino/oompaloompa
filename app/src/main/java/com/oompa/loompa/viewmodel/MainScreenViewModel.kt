package com.oompa.loompa.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.oompa.loompa.data.database.OompaLoompaDatabase
import com.oompa.loompa.data.model.OompaLoompa
import com.oompa.loompa.data.OompaLoompasRemoteMediator
import com.oompa.loompa.data.service.OompaLoompaApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val PAGE_SIZE = 20

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    oompaLoompaApiService: OompaLoompaApiService,
    oompaLoompaDatabase: OompaLoompaDatabase,
): ViewModel() {
    private val oompaLoompasDao = oompaLoompaDatabase.getOompaLoompasDao()
    private lateinit var currentPagingSource: PagingSource<Int, OompaLoompa>
    var filterByGenders by mutableStateOf(listOf<String>())
        private set
    var filterByProfessions by mutableStateOf(listOf<String>())
        private set

    @OptIn(ExperimentalPagingApi::class)
    val oompaLoompas =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            pagingSourceFactory = {
                currentPagingSource = oompaLoompasDao.getOompaLoompas(
                    oompaLoompasDao.createOompaLoompasQuery(filterByGenders, filterByProfessions))
                currentPagingSource
            },
            remoteMediator = OompaLoompasRemoteMediator(
                oompaLoompaApiService,
                oompaLoompaDatabase,
            )
        ).flow.cachedIn(viewModelScope)

    fun getProfessions(): Flow<List<String>> {
        return oompaLoompasDao.getProfessions()
    }

    private fun onGenderFilterChanged(genders: List<String>) {
        filterByGenders = genders
        currentPagingSource.invalidate()
    }

    private fun onProfessionFilterChanged(professions: List<String>) {
        filterByProfessions = professions
        currentPagingSource.invalidate()
    }

    fun getFilteringState(): OompaLoompaFilteringState {
        return OompaLoompaFilteringState(
            selectedGenders = filterByGenders,
            onGenderFilterChanged = { onGenderFilterChanged(it) },
            selectedProfessions = filterByProfessions,
            onProfessionFilterChanged = { onProfessionFilterChanged(it) },
        )
    }
}