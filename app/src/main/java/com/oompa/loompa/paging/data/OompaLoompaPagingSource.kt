package com.oompa.loompa.paging.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oompa.loompa.model.OompaLoompa
import com.oompa.loompa.service.OompaLoompaApiService

class OompaLoompaPagingSource(
    private val oompaLoompaApiService: OompaLoompaApiService,
) : PagingSource<Int, OompaLoompa>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OompaLoompa> {
        return try {
            val page = params.key ?: 1
            val response = oompaLoompaApiService.getPage(page = page)

            LoadResult.Page(
                data = response.oompaLoompas,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (page == response.total) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, OompaLoompa>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}