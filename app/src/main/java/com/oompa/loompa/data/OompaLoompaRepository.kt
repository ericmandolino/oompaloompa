package com.oompa.loompa.data

import com.oompa.loompa.data.database.OompaLoompaDatabase
import com.oompa.loompa.data.model.OompaLoompa
import com.oompa.loompa.data.model.OompaLoompaApiResponse
import com.oompa.loompa.data.model.OompaLoompaExtraDetails
import com.oompa.loompa.data.service.OompaLoompaApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

const val EXTRA_DETAILS_CACHE_TIMEOUT_MILLISECONDS = 10 * 60 * 1000 // TODO: extract to consts and inject to constructor?

class OompaLoompaRepository @Inject constructor(
    private val oompaLoompaApiService: OompaLoompaApiService,
    oompaLoompaDatabase: OompaLoompaDatabase,
) {
    private val oompaLoompasDao = oompaLoompaDatabase.getOompaLoompasDao()

    fun getOompaLoompaExtraDetails(
        coroutineScope: CoroutineScope,
        oompaLoompaId: Long,
    ): Flow<Map<OompaLoompa, OompaLoompaExtraDetails>> {
        checkCache(coroutineScope, oompaLoompaId)
        return oompaLoompasDao.observeOompaLoompaWithExtraDetails(oompaLoompaId)
    }

    private fun checkCache(
        coroutineScope: CoroutineScope,
        oompaLoompaId: Long,
    ) = coroutineScope.launch {
        if (!isInCacheAndHasNotExpired(oompaLoompaId)) {
            fetchOompaLoompaExtraDetails(coroutineScope, oompaLoompaId)
        }
    }

    private fun updateCache(
        coroutineScope: CoroutineScope,
        oompaLoompaId: Long,
        oompaLoompaApiResponse: OompaLoompaApiResponse,
    ) = coroutineScope.launch {
        updateInCache(oompaLoompaId, oompaLoompaApiResponse)
    }

    private fun fetchOompaLoompaExtraDetails(
        coroutineScope: CoroutineScope,
        oompaLoompaId: Long,
    ) {
        val call = oompaLoompaApiService.getOompaLoompa(oompaLoompaId)
        call.enqueue(object:  Callback<OompaLoompaApiResponse> {
            override fun onFailure(call: Call<OompaLoompaApiResponse>, t: Throwable) {
                // TODO
            }

            override fun onResponse(call: Call<OompaLoompaApiResponse>, response: Response<OompaLoompaApiResponse>) {
                val oompaLoompaExtraDetails = response.body()
                if (oompaLoompaExtraDetails != null) {
                    updateCache(coroutineScope, oompaLoompaId, oompaLoompaExtraDetails)
                } // TODO else (?)
            }
        })
    }

    private suspend fun updateInCache(oompaLoompaId: Long, oompaLoompaApiResponse: OompaLoompaApiResponse) {
        val oompaLoompaExtraDetails = OompaLoompaExtraDetails(
            id = oompaLoompaId,
            description = oompaLoompaApiResponse.description,
            quota = oompaLoompaApiResponse.quota,
        )
        oompaLoompasDao.insert(oompaLoompaExtraDetails)
    }

    private suspend fun isInCacheAndHasNotExpired(oompaLoompaId: Long): Boolean {
        val cachedOompaLoompaExtraDetails = oompaLoompasDao.getOompaLoompaExtraDetails(oompaLoompaId)

        return cachedOompaLoompaExtraDetails != null &&
                System.currentTimeMillis() - cachedOompaLoompaExtraDetails.createdAt < EXTRA_DETAILS_CACHE_TIMEOUT_MILLISECONDS
    }
}