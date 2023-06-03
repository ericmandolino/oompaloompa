package com.oompa.loompa.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oompa.loompa.data.model.OompaLoompa
import com.oompa.loompa.data.model.OompaLoompaExtraDetails
import com.oompa.loompa.viewmodel.OompaLoompaViewModel

@Composable
fun OompaLoompaDetailsScreen(
    oompaLoompaViewModel: OompaLoompaViewModel,
    oompaLoompaId: Long?,
) {
    val oompaLoompaDetails = if (oompaLoompaId != null) oompaLoompaViewModel.getOompaLoompaWithExtraDetails(oompaLoompaId).collectAsState(null).value else null
    if (oompaLoompaDetails?.size == 1) {
        val entry = oompaLoompaDetails.iterator().next()
        OompaLoompaDetails(entry.key, entry.value)
    } else {
        OompaLoompaNoDetails()
    }
}

@Composable
fun OompaLoompaDetails(
    oompaLoompa: OompaLoompa,
    oompaLoompaExtraDetails: OompaLoompaExtraDetails,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(text = oompaLoompa.firstName)
        Text(text = oompaLoompa.lastName)
        Text(text = oompaLoompaExtraDetails.description)
        Text(text = oompaLoompaExtraDetails.quota)
    }
}

@Composable
fun OompaLoompaNoDetails() {
    Text(text = "No details available :(")
}