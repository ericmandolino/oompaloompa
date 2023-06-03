package com.oompa.loompa.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.oompa.loompa.R
import com.oompa.loompa.viewmodel.OompaLoompaViewModel

@Composable
fun PagingOompaLoompas(
    oompaLoompaViewModel: OompaLoompaViewModel,
    onNavigateToOompaLoompaDetails: (oompaLoompaId: Long) -> Unit,
    paddingValues: PaddingValues,
) {
    val oompaLoompas = oompaLoompaViewModel.oompaLoompas.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(count = oompaLoompas.itemCount) { index ->
            val oompaLoompa = oompaLoompas[index]
            if (oompaLoompa != null) {
                OompaLoompaCard(
                    oompaLoompa = oompaLoompa,
                    clickable = true,
                    onClick = onNavigateToOompaLoompaDetails,
                    clickLabel = stringResource(R.string.go_to_details),
                )
            } else {
                OompaLoompaPlaceholder()
            }
        }
        item {
            Spacer(modifier = Modifier.height(96.dp))
        }
    }
}

@Composable
fun OompaLoompaPlaceholder() {
    Card( modifier = Modifier
        .fillMaxWidth()
        .height(48.dp) ) { }
}