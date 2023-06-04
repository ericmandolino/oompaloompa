package com.oompa.loompa.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oompa.loompa.data.OompaLoompaRepository
import com.oompa.loompa.data.model.OompaLoompa
import com.oompa.loompa.data.model.OompaLoompaExtraDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val oompaLoompaRepository: OompaLoompaRepository,
): ViewModel() {
    var showLongDetail by mutableStateOf(false)
        private set
    var longDetailName by mutableStateOf("")
        private set
    var longDetailValue by mutableStateOf("")
        private set

    fun getOompaLoompaWithExtraDetails(oompaLoompaId: Long): Flow<Map<OompaLoompa, OompaLoompaExtraDetails>> {
        return oompaLoompaRepository.getOompaLoompaExtraDetails(
            coroutineScope = viewModelScope,
            oompaLoompaId = oompaLoompaId,
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
            }
            },
            onHideLongDetail = { showLongDetail = false },
        )
    }
}