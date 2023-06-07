package com.oompa.loompa.viewmodel

import com.oompa.loompa.data.model.OompaLoompa
import com.oompa.loompa.data.model.OompaLoompaExtraDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface OompaLoompaRepository {
    fun getOompaLoompa(oompaLoompaId: Long): Flow<OompaLoompa>

    fun getOompaLoompaExtraDetails(
        coroutineScope: CoroutineScope,
        oompaLoompaId: Long,
        onApiFailure: () -> Unit,
    ): Flow<OompaLoompaExtraDetails>

    fun fetchOompaLoompaExtraDetails(
        coroutineScope: CoroutineScope,
        oompaLoompaId: Long,
        onApiFailure: () -> Unit,
    )
}