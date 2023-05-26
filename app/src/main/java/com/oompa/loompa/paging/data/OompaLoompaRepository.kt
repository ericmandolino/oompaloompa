package com.oompa.loompa.paging.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.oompa.loompa.service.OompaLoompaApiService
import javax.inject.Inject

class OompaLoompaRepository @Inject constructor(
    private val oompaLoompaApiService: OompaLoompaApiService
) {
    fun getOompaLoompas() = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            OompaLoompaPagingSource(oompaLoompaApiService)
        }
    )
}