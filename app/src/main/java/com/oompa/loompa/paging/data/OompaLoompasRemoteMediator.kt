package com.oompa.loompa.paging.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.oompa.loompa.database.OompaLoompaDatabase
import com.oompa.loompa.model.OompaLoompa
import com.oompa.loompa.model.RemoteKeys
import com.oompa.loompa.service.OompaLoompaApiService
import retrofit2.HttpException
import java.io.IOException

const val CACHE_TIMEOUT_MILLISECONDS = 3600000

@OptIn(ExperimentalPagingApi::class)
class OompaLoompasRemoteMediator(
    private val oompaLoompaApiService: OompaLoompaApiService,
    private val oompaLoompaDatabase: OompaLoompaDatabase,
): RemoteMediator<Int, OompaLoompa>() {
    private val oompaLoompasDao = oompaLoompaDatabase.getOompaLoompasDao()
    private val remoteKeysDao = oompaLoompaDatabase.getRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        return if (System.currentTimeMillis() - (remoteKeysDao.getCreationTime()?: 0) < CACHE_TIMEOUT_MILLISECONDS) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, OompaLoompa>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.prevKey?.plus(1)?: remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val apiResponse = oompaLoompaApiService.getPage(page = page)

            val oompaLoompas = apiResponse.oompaLoompas
            val endOfPaginationReached = page == apiResponse.total

            oompaLoompaDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys()
                    oompaLoompasDao.clearAllOompaLoompas()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = oompaLoompas.map {
                    RemoteKeys(oompaLoompaId = it.id, prevKey = prevKey, currentPage = page, nextKey = nextKey)
                }

                remoteKeysDao.insertAll(remoteKeys)
                oompaLoompasDao.insertAll(oompaLoompas)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, OompaLoompa>): RemoteKeys? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.id?.let { id ->
                remoteKeysDao.getRemoteKeyByOompaLoompaId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, OompaLoompa>): RemoteKeys? {
        return state.firstItemOrNull()?.let { oompaLoompa ->
            remoteKeysDao.getRemoteKeyByOompaLoompaId(oompaLoompa.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, OompaLoompa>): RemoteKeys? {
        return state.lastItemOrNull()?.let { oompaLoompa ->
            remoteKeysDao.getRemoteKeyByOompaLoompaId(oompaLoompa.id)
        }
    }
}