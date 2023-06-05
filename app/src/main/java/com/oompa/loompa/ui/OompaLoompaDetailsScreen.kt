package com.oompa.loompa.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oompa.loompa.R
import com.oompa.loompa.data.model.OompaLoompa
import com.oompa.loompa.data.model.OompaLoompaExtraDetails
import com.oompa.loompa.data.model.OompaLoompaFavorite
import com.oompa.loompa.ui.theme.LoompaTheme
import com.oompa.loompa.viewmodel.DetailsScreenViewModel
import com.oompa.loompa.viewmodel.OompaLoompaLongDetailState
import com.oompa.loompa.viewmodel.OompaLoompaRefreshExtraDetailsState

@Composable
fun OompaLoompaDetailsScreen(
    detailsScreenViewModel: DetailsScreenViewModel,
    oompaLoompaId: Long?,
) {
    val oompaLoompa = if (oompaLoompaId != null) detailsScreenViewModel.getOompaLoompa(oompaLoompaId).collectAsState(null).value else null
    val oompaLoompaExtraDetails = if (oompaLoompaId != null) detailsScreenViewModel.getOompaLoompaExtraDetails(oompaLoompaId).collectAsState(null).value else null
    OompaLoompaDetails(
        oompaLoompa,
        oompaLoompaExtraDetails,
        refreshExtraDetailsState = detailsScreenViewModel.getRefreshExtraDetailsState(oompaLoompaId),
        longDetailState = detailsScreenViewModel.getLongDetailState(),
    )
}

@Composable
fun OompaLoompaDetails(
    oompaLoompa: OompaLoompa?,
    oompaLoompaExtraDetails: OompaLoompaExtraDetails?,
    refreshExtraDetailsState: OompaLoompaRefreshExtraDetailsState,
    longDetailState: OompaLoompaLongDetailState,
) {
    Box(modifier =
        Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            MainDetails(
                oompaLoompa,
                longDetailState,
            )
            ExtraDetails(
                oompaLoompaExtraDetails,
                refreshExtraDetailsState,
                longDetailState,
            )
        }
        if (longDetailState.showLongDetail) {
            LongDetail(
                detailName = longDetailState.longDetailName,
                detailValue = longDetailState.longDetailValue,
                onCloseLongDetailClicked = longDetailState.onHideLongDetail,
            )
        }
    }
}

@Composable
fun MainDetails(
    oompaLoompa: OompaLoompa?,
    longDetailState: OompaLoompaLongDetailState,
) {
    if (oompaLoompa != null) {
        OompaLoompaCard(
            oompaLoompa,
            startExpanded = true,
            clickable = false,
        )
        OompaLoompaFavoriteCard(
            oompaLoompaFavorite = oompaLoompa.favorite,
            longDetailState,
        )
    } else {
        OompaLoompaNoDetailsCard()
        OompaLoompaNoDetailsCard()
    }
}

@Composable
fun ExtraDetails(
    oompaLoompaExtraDetails: OompaLoompaExtraDetails?,
    refreshExtraDetailsState: OompaLoompaRefreshExtraDetailsState,
    longDetailState: OompaLoompaLongDetailState,
) {
    if (oompaLoompaExtraDetails != null) {
        OompaLoompaExtraDetailsCard(
            oompaLoompaExtraDetails,
            longDetailState,
        )
    } else if (refreshExtraDetailsState.showRefreshExtraDetails) {
        RetryCard(
            onRetry = refreshExtraDetailsState.onRefreshExtraDetails
        )
    } else {
        OompaLoompaNoDetailsCard()
    }
}

@Composable
fun OompaLoompaNoDetailsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun OompaLoompaFavoriteCard(
    oompaLoompaFavorite: OompaLoompaFavorite,
    longDetailState: OompaLoompaLongDetailState,
) {
    Card {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                text = stringResource(R.string.favorite),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            SecondaryLine(name = stringResource(R.string.food), value = oompaLoompaFavorite.food)
            SecondaryLine(name = stringResource(R.string.color), value = oompaLoompaFavorite.color)
            DetailBlock(
                blockName = stringResource(R.string.song),
                blockValue = oompaLoompaFavorite.song,
                longDetailState,
            )
            DetailBlock(
                blockName = stringResource(R.string.random_string),
                blockValue = oompaLoompaFavorite.randomString,
                longDetailState,
            )
        }
    }
}

@Composable
fun OompaLoompaExtraDetailsCard(
    oompaLoompaExtraDetails: OompaLoompaExtraDetails,
    longDetailState: OompaLoompaLongDetailState,
) {
    Card {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                text = stringResource(R.string.extra_details),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            DetailBlock(
                blockName = stringResource(R.string.description),
                blockValue = oompaLoompaExtraDetails.description,
                longDetailState,
            )
            DetailBlock(
                blockName = stringResource(R.string.quota),
                blockValue = oompaLoompaExtraDetails.quota,
                longDetailState,
            )
        }
    }
}

@Composable
fun DetailBlock(
    blockName: String,
    blockValue: String,
    longDetailState: OompaLoompaLongDetailState,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                longDetailState.onShowLongDetail(blockName, blockValue)
            }
    ) {
        Row {
            Text(
                text = "$blockName:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                modifier = Modifier
                    .size(16.dp),
                contentDescription = stringResource(R.string.show),
            )
        }
        Row(
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 4.dp,
                )
        ) {
            Text(
                text = blockValue,
                maxLines = 5,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun LongDetail(
    detailName: String,
    detailValue: String,
    onCloseLongDetailClicked: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.Transparent,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Row (
                        modifier = Modifier
                            .wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = detailName,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = onCloseLongDetailClicked
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.close),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = detailValue,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun OompaLoompaFavoriteCardPreview() {
    val oompaLoompaFavorite = PreviewUtil.getPreviewOompaLoompaFavorite()
    LoompaTheme {
        OompaLoompaFavoriteCard(
            oompaLoompaFavorite = oompaLoompaFavorite,
            longDetailState = OompaLoompaLongDetailState(
                showLongDetail = false,
                longDetailName = "",
                longDetailValue = "",
                onShowLongDetail = { _: String,  _: String -> run {} },
                onHideLongDetail = {},
            ),
        )
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun OompaLoompaExtraDetailsCardPreview() {

    val oompaLoompaExtraDetails = PreviewUtil.getPreviewOompaLoompaExtraDetails()
    LoompaTheme {
        OompaLoompaExtraDetailsCard(
            oompaLoompaExtraDetails = oompaLoompaExtraDetails,
            longDetailState = OompaLoompaLongDetailState(
                showLongDetail = false,
                longDetailName = "",
                longDetailValue = "",
                onShowLongDetail = { _: String,  _: String -> run {} },
                onHideLongDetail = {},
            ),
        )
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun LongDetailPreview() {
    LoompaTheme {
        LongDetail(
            detailName = "Song",
            detailValue = PreviewUtil.getPreviewOompaLoompa().favorite.song,
            onCloseLongDetailClicked = {},
        )
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun OompaLoompaNoDetailsCardPreview() {
    LoompaTheme {
        OompaLoompaNoDetailsCard()
    }
}