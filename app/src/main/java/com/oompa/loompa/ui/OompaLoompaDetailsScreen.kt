package com.oompa.loompa.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oompa.loompa.R
import com.oompa.loompa.data.model.OompaLoompa
import com.oompa.loompa.data.model.OompaLoompaExtraDetails
import com.oompa.loompa.ui.theme.LoompaTheme
import com.oompa.loompa.viewmodel.OompaLoompaLongDetailState
import com.oompa.loompa.viewmodel.OompaLoompaViewModel

@Composable
fun OompaLoompaDetailsScreen(
    oompaLoompaViewModel: OompaLoompaViewModel,
    oompaLoompaId: Long?,
) {
    val oompaLoompaDetails = if (oompaLoompaId != null) oompaLoompaViewModel.getOompaLoompaWithExtraDetails(oompaLoompaId).collectAsState(null).value else null
    if (oompaLoompaDetails?.size == 1) {
        val entry = oompaLoompaDetails.iterator().next()
        OompaLoompaDetails(
            oompaLoompa = entry.key,
            oompaLoompaExtraDetails = entry.value,
            longDetailState = oompaLoompaViewModel.getLongDetailState(),
        )
    } else {
        OompaLoompaNoDetails()
    }
}

@Composable
fun OompaLoompaDetails(
    oompaLoompa: OompaLoompa,
    oompaLoompaExtraDetails: OompaLoompaExtraDetails,
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
            OompaLoompaCard(
                oompaLoompa = oompaLoompa,
                startExpanded = true,
                clickable = false,
            )
            OompaLoompaFavoriteCard(
                oompaLoompaFavorite = oompaLoompa.favorite,
                longDetailState,
            )
            OompaLoompaExtraDetailsCard(
                oompaLoompaExtraDetails = oompaLoompaExtraDetails,
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
fun OompaLoompaNoDetails() {
    Text(text = "No details available :(") // TODO: loading (?) refresh (?)
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
fun LongDetailPreview() {
    LoompaTheme {
        LongDetail(
            detailName = "Song",
            detailValue = "Oompa Loompas:\nOompa Loompa doompadee doo\nI've got another puzzle for you\nOompa Loompa doompadah dee\nIf you are wise you'll listen to me\nWhat do you get from a glut of TV?\nA pain in the neck and an IQ of three\nWhy don't you try simply reading a book?\nOr could you just not bear to look?\nYou'll get no\nYou'll get no\nYou'll get no\nYou'll get no\nYou'll get no commercials\nOompa Loompa Doompadee Dah\nIf you're like reading you will go far\nYou will live in happiness too\nLike the Oompa\nOompa Loompa doompadee do\nOompa Loompas:\nOompa Loompa doompadee doo\nI've got another puzzle for you\nOompa Loompa doompadah dee\nIf you are wise you'll listen to me\nWhat do you get from a glut of TV?\nA pain in the neck and an IQ of three\nWhy don't you try simply reading a book?\nOr could you just not bear to look?\nYou'll get no\nYou'll get no\nYou'll get no\nYou'll get no\nYou'll get no commercials\nOompa Loompa Doompadee Dah\nIf you're like reading you will go far\nYou will live in happiness too\nLike the Oompa\nOompa Loompa doompadee do\nOompa Loompas:\nOompa Loompa doompadee doo\nI've got another puzzle for you\nOompa Loompa doompadah dee\nIf you are wise you'll listen to me\nWhat do you get from a glut of TV?\nA pain in the neck and an IQ of three\nWhy don't you try simply reading a book?\nOr could you just not bear to look?\nYou'll get no\nYou'll get no\nYou'll get no\nYou'll get no\nYou'll get no commercials\nOompa Loompa Doompadee Dah\nIf you're like reading you will go far\nYou will live in happiness too\nLike the Oompa\nOompa Loompa doompadee do\nOompa Loompas:\nOompa Loompa doompadee doo\nI've got another puzzle for you\nOompa Loompa doompadah dee\nIf you are wise you'll listen to me\nWhat do you get from a glut of TV?\nA pain in the neck and an IQ of three\nWhy don't you try simply reading a book?\nOr could you just not bear to look?\nYou'll get no\nYou'll get no\nYou'll get no\nYou'll get no\nYou'll get no commercials\nOompa Loompa Doompadee Dah\nIf you're like reading you will go far\nYou will live in happiness too\nLike the Oompa\nOompa Loompa doompadee do\nOompa Loompas:\nOompa Loompa doompadee doo\nI've got another puzzle for you\nOompa Loompa doompadah dee\nIf you are wise you'll listen to me\nWhat do you get from a glut of TV?\nA pain in the neck and an IQ of three\nWhy don't you try simply reading a book?\nOr could you just not bear to look?\nYou'll get no\nYou'll get no\nYou'll get no\nYou'll get no\nYou'll get no commercials\nOompa Loompa Doompadee Dah\nIf you're like reading you will go far\nYou will live in happiness too\nLike the Oompa\nOompa Loompa doompadee do\nOompa Loompas:\nOompa Loompa doompadee doo\nI've got another puzzle for you\nOompa Loompa doompadah dee\nIf you are wise you'll listen to me\nWhat do you get from a glut of TV?\nA pain in the neck and an IQ of three\nWhy don't you try simply reading a book?\nOr could you just not bear to look?\nYou'll get no\nYou'll get no\nYou'll get no\nYou'll get no\nYou'll get no commercials\nOompa Loompa Doompadee Dah\nIf you're like reading you will go far\nYou will live in happiness too\nLike the Oompa\nOompa Loompa doompadee do",
            onCloseLongDetailClicked = {},
        )
    }
}