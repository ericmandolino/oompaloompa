package com.oompa.loompa.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.oompa.loompa.data.connectivity.ConnectivityMonitor
import com.oompa.loompa.data.model.OompaLoompa
import com.oompa.loompa.data.model.OompaLoompaExtraDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val oompaLoompaRepository: OompaLoompaRepository,
    connectivityMonitor: ConnectivityMonitor,
): AutomaticRetryViewModel(connectivityMonitor) {
    private var showLongDetail by mutableStateOf(false)
    private var longDetailName by mutableStateOf("")
    private var longDetailValue by mutableStateOf("")
    private var showRefreshExtraDetails by mutableStateOf(false)

    fun getOompaLoompa(oompaLoompaId: Long): Flow<OompaLoompa> {
        return oompaLoompaRepository.getOompaLoompa(
            oompaLoompaId = oompaLoompaId,
        )
    }

    fun getOompaLoompaExtraDetails(oompaLoompaId: Long): Flow<OompaLoompaExtraDetails> {
        return oompaLoompaRepository.getOompaLoompaExtraDetails(
            coroutineScope = viewModelScope,
            oompaLoompaId = oompaLoompaId,
            onApiFailure = {
                showRefreshExtraDetails = true
            },
        )
    }

    fun getRefreshExtraDetailsState(oompaLoompaId: Long?): OompaLoompaRefreshExtraDetailsState {
        return OompaLoompaRefreshExtraDetailsState(
            showRefreshExtraDetails,
            onRefreshExtraDetails =
            if (oompaLoompaId != null) {
                {
                    showRefreshExtraDetails = false
                    oompaLoompaRepository.fetchOompaLoompaExtraDetails(
                        coroutineScope = viewModelScope,
                        oompaLoompaId = oompaLoompaId,
                        onApiFailure = {
                            showRefreshExtraDetails = true
                        },
                    )
                }
            } else {
                {}
            }
        )
    }

    fun getLongDetailState(): OompaLoompaLongDetailState {
        return OompaLoompaLongDetailState(
            showLongDetail,
            longDetailName,
            longDetailValue,
            onShowLongDetail = { name: String, value: String -> run {
                showLongDetail = true
                longDetailName = name
                longDetailValue = value
            }},
            onHideLongDetail = { showLongDetail = false },
        )
    }
}