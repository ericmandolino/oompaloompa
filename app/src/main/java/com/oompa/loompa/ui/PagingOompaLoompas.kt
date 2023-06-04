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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.oompa.loompa.R
import com.oompa.loompa.data.model.OompaLoompa
import com.oompa.loompa.viewmodel.MainScreenViewModel

@Composable
fun PagingOompaLoompas(
    oompaLoompaViewModel: MainScreenViewModel,
    onNavigateToOompaLoompaDetails: (oompaLoompaId: Long) -> Unit,
    paddingValues: PaddingValues,
) {
    val oompaLoompas = oompaLoompaViewModel.oompaLoompas.collectAsLazyPagingItems()
    val pageLoadState = oompaLoompas.loadState

    when (pageLoadState.refresh) {
        is LoadState.Error -> {
            RetryCard (
                onRetry = {
                    oompaLoompas.refresh()
                }
            )
        }
        is LoadState.Loading -> {
            FullScreenLoading()
        }
        else -> {
            OompaLoompaPagingList(
                oompaLoompas,
                onNavigateToOompaLoompaDetails,
                paddingValues
            )
        }
    }
}

@Composable
fun OompaLoompaPagingList(
    oompaLoompas: LazyPagingItems<OompaLoompa>,
    onNavigateToOompaLoompaDetails: (oompaLoompaId: Long) -> Unit,
    paddingValues: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            OompaLoompaPagingPrependItem(
                oompaLoompas
            )
        }
        items(
            count = oompaLoompas.itemCount,
            contentType = { index -> if (oompaLoompas[index] != null) 1 else 2 },
        ) { index ->
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
            OompaLoompaPagingAppendItem(
                oompaLoompas
            )
        }
        item {
            Spacer(modifier = Modifier.height(96.dp))
        }
    }
}

@Composable
fun OompaLoompaPagingPrependItem(
    oompaLoompas: LazyPagingItems<OompaLoompa>,
) {
    val pageLoadState = oompaLoompas.loadState

    when (pageLoadState.prepend) {
        is LoadState.Loading -> {
            LoadingCard()
        }
        is LoadState.Error -> {
            RetryCard (
                onRetry = {
                    oompaLoompas.retry()
                }
            )
        }
        else -> {
        }
    }
}

@Composable
fun OompaLoompaPagingAppendItem(
    oompaLoompas: LazyPagingItems<OompaLoompa>,
) {
    val pageLoadState = oompaLoompas.loadState

    when (pageLoadState.append) {
        is LoadState.Loading -> {
            LoadingCard()
        }
        is LoadState.Error -> {
            RetryCard (
                onRetry = {
                    oompaLoompas.retry()
                }
            )
        }
        else -> {
        }
    }
}

@Composable
fun OompaLoompaPlaceholder() {
    Card( modifier = Modifier
        .fillMaxWidth()
        .height(48.dp) ) { }
}