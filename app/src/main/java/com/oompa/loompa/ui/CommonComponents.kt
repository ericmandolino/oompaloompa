package com.oompa.loompa.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.oompa.loompa.R
import com.oompa.loompa.data.model.OompaLoompa
import com.oompa.loompa.ui.theme.LoompaTheme
import com.oompa.loompa.viewmodel.AutomaticRetryState


@Composable
fun OompaLoompaCardItem(
    oompaLoompa: OompaLoompa,
    clickable: Boolean = false,
    onClick: (oompaLoompaId: Long) -> Unit = {},
    clickLabel: String? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = clickable,
                onClickLabel = clickLabel,
            ) {
                onClick(oompaLoompa.id)
            }
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        AsyncImage(
            model = oompaLoompa.image,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentDescription = stringResource(R.string.profile_picture),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            Modifier
                .padding(8.dp),
        ) {
            Text(
                text = "${oompaLoompa.firstName} ${oompaLoompa.lastName}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = "${oompaLoompa.profession} (${oompaLoompa.gender})",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = "ID ${oompaLoompa.id}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
fun OompaLoompaCard(
    oompaLoompa: OompaLoompa,
    startExpanded: Boolean = false,
    clickable: Boolean = false,
    onClick: (oompaLoompaId: Long) -> Unit = {},
    clickLabel: String? = null,
) {
    var expanded by rememberSaveable { mutableStateOf(startExpanded) }
    Card(
        Modifier
            .clickable(
                enabled = clickable,
                onClickLabel = clickLabel,
            ) {
                onClick(oompaLoompa.id)
            },
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = oompaLoompa.image,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentDescription = stringResource(R.string.profile_picture),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier
                .weight(1f)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            ) {
                OompaLoompaCardMain(oompaLoompa = oompaLoompa)
                if (expanded) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OompaLoompaCardSecondary(oompaLoompa = oompaLoompa)
                }
            }
            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .align(Alignment.Top),
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) stringResource(R.string.show_more) else stringResource(
                        R.string.show_less)
                )
            }
        }
    }
}

@Composable
fun OompaLoompaCardMain(oompaLoompa: OompaLoompa) {
    Text(
        text = "${oompaLoompa.firstName} ${oompaLoompa.lastName}",
        style = MaterialTheme.typography.titleLarge,
    )
    Text(
        text = "${oompaLoompa.profession} (${oompaLoompa.gender})",
        style = MaterialTheme.typography.titleMedium,
    )
    Text(
        text = "ID ${oompaLoompa.id}",
        style = MaterialTheme.typography.titleSmall,
    )
}

@Composable
fun OompaLoompaCardSecondary(oompaLoompa: OompaLoompa) {
    SecondaryLine(name = stringResource(R.string.email), value = oompaLoompa.email)
    SecondaryLine(name = stringResource(R.string.age), value = oompaLoompa.age.toString())
    SecondaryLine(name = stringResource(R.string.height), value = oompaLoompa.height.toString())
    SecondaryLine(name = stringResource(R.string.country), value = oompaLoompa.country)
}

@Composable
fun SecondaryLine(name: String, value: String) {
    Row {
        Text(
            text = "$name: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun RetryCard(
    onRetry: () -> Unit,
    automaticRetryState: AutomaticRetryState,
) {
    val retry = {
        onRetry()
        automaticRetryState.updateAutomaticRetry(false)
    }
    if (automaticRetryState.shouldRetryAutomatically) {
        retry()
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = retry,
            ) {
                Text(
                    text = stringResource(R.string.retry)
                )
            }
        }
    }
}

@Composable
fun LoadingCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)) {
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
fun FullScreenLoading() {
    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.loading),
                style = MaterialTheme.typography.displaySmall,
            )
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun OompaLoompaCardItemPreview() {
    val oompaLoompa = PreviewUtil.getPreviewOompaLoompa()
    LoompaTheme {
        OompaLoompaCardItem(
            oompaLoompa = oompaLoompa,
            clickable = true,
            onClick = {},
            clickLabel = "click label",
        )
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun OompaLoompaCardPreview() {
    val oompaLoompa = PreviewUtil.getPreviewOompaLoompa()
    LoompaTheme {
        OompaLoompaCard(
            oompaLoompa = oompaLoompa,
            clickable = true,
            onClick = {},
            clickLabel = "click label",
        )
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun OompaLoompaRefreshExtraDetailsCardPreview() {
    LoompaTheme {
        RetryCard(
            onRetry = {},
            automaticRetryState = AutomaticRetryState(
                shouldRetryAutomatically = false,
                updateAutomaticRetry = {},
            ),
        )
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun FullScreenLoadingPreview() {
    LoompaTheme {
        FullScreenLoading()
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun LoadingCardPreview() {
    LoompaTheme {
        LoadingCard()
    }
}