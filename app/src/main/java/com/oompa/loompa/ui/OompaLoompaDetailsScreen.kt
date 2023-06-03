package com.oompa.loompa.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.oompa.loompa.model.OompaLoompaExtraDetails
import com.oompa.loompa.viewmodel.OompaLoompaViewModel2

@Composable
fun OompaLoompaDetailsScreen(
    oompaLoompaViewModel: OompaLoompaViewModel2,
    oompaLoompaId: Long?,
) {
    val oompaLoompaDetails = if (oompaLoompaId != null) oompaLoompaViewModel.getOompaLoompaWithExtraDetails(oompaLoompaId).collectAsState(null).value else null
    if (oompaLoompaDetails != null) {
        OompaLoompaDetails(oompaLoompaDetails)
    } else {
        OompaLoompaNoDetails()
    }
}

@Composable
fun OompaLoompaDetails(oompaLoompaDetails: OompaLoompaExtraDetails) {
    Column {
        Text(text = oompaLoompaDetails.description)
        Text(text = oompaLoompaDetails.quota)
    }
}

@Composable
fun OompaLoompaNoDetails() {
    Text(text = "No details available :(")
}