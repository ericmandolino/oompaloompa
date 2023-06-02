package com.oompa.loompa.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.gson.Gson
import com.oompa.loompa.R
import com.oompa.loompa.model.OompaLoompa
import com.oompa.loompa.ui.theme.LoompaTheme
import com.oompa.loompa.viewmodel.OompaLoompaViewModel2

@Composable
fun PagingOompaLoompas(oompaLoompaViewModel: OompaLoompaViewModel2, paddingValues: PaddingValues) {
    val oompaLoompas = oompaLoompaViewModel.getOompaLoompas().collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(count = oompaLoompas.itemCount) { index ->
            val oompaLoompa = oompaLoompas[index]
            if (oompaLoompa != null) {
                OompaLoompaCard(oompaLoompa)
            } else {
                OompaLoompaPlaceholder()
            }
            if (index == oompaLoompas.itemCount - 1) {
                Spacer(modifier = Modifier.height(96.dp))
            }
        }
    }
}

@Composable
fun OompaLoompaPlaceholder() {
    Card( modifier = Modifier
        .fillMaxWidth()
        .height(48.dp) ) { }
}

@Composable
fun OompaLoompaCard(oompaLoompa: OompaLoompa) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Card(
        Modifier.defaultMinSize(minHeight = 48.dp),
    ) {
        Row {
            Column(modifier = Modifier
                .padding(8.dp)
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
                    OompaLoompaCardSecondary(oompaLoompa = oompaLoompa)
                }
            }
            IconButton(
                onClick = { expanded = !expanded }
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
        fontSize = 16.sp,
    )
    Text(
        text = "${oompaLoompa.profession} (${oompaLoompa.gender})",
        fontSize = 12.sp,
    )
    Text(
        text = "${oompaLoompa.id}",
        fontSize = 12.sp,
    )
}

@Composable
fun OompaLoompaCardSecondary(oompaLoompa: OompaLoompa) {
    Row {
        Spacer(modifier = Modifier
            .height(4.dp)
            .width(8.dp))
        Column {
            SecondaryLine(line = "${stringResource(R.string.email)}: ${oompaLoompa.email}")
            SecondaryLine(line = "${stringResource(R.string.age)}: ${oompaLoompa.age}")
            SecondaryLine(line = "${stringResource(R.string.height)}: ${oompaLoompa.height}")
            SecondaryLine(line = "${stringResource(R.string.country)}: ${oompaLoompa.country}")
        }
    }
}

@Composable
fun SecondaryLine(line: String) {
    Text(text = line, fontSize = 8.sp)
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun OompaLoompaCardPreview() {
    val oompaLoompa = Gson().fromJson("{\n" +
            "      \"first_name\": \"Marcy\",\n" +
            "      \"last_name\": \"Karadzas\",\n" +
            "      \"favorite\": {\n" +
            "        \"color\": \"red\",\n" +
            "        \"food\": \"Chocolat\",\n" +
            "        \"random_string\": \"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\",\n" +
            "        \"song\": \"Oompa Loompas:\\nOompa Loompa doompadee doo\\nI've got another puzzle for you\\nOompa Loompa doompadah dee\\nIf you are wise you'll listen to me\\nWhat do you get from a glut of TV?\\nA pain in the neck and an IQ of three\\nWhy don't you try simply reading a book?\\nOr could you just not bear to look?\\nYou'll get no\\nYou'll get no\\nYou'll get no\\nYou'll get no\\nYou'll get no commercials\\nOompa Loompa Doompadee Dah\\nIf you're like reading you will go far\\nYou will live in happiness too\\nLike the Oompa\\nOompa Loompa doompadee do\\nOompa Loompas:\\nOompa Loompa doompadee doo\\nI've got another puzzle for you\\nOompa Loompa doompadah dee\\nIf you are wise you'll listen to me\\nWhat do you get from a glut of TV?\\nA pain in the neck and an IQ of three\\nWhy don't you try simply reading a book?\\nOr could you just not bear to look?\\nYou'll get no\\nYou'll get no\\nYou'll get no\\nYou'll get no\\nYou'll get no commercials\\nOompa Loompa Doompadee Dah\\nIf you're like reading you will go far\\nYou will live in happiness too\\nLike the Oompa\\nOompa Loompa doompadee do\\nOompa Loompas:\\nOompa Loompa doompadee doo\\nI've got another puzzle for you\\nOompa Loompa doompadah dee\\nIf you are wise you'll listen to me\\nWhat do you get from a glut of TV?\\nA pain in the neck and an IQ of three\\nWhy don't you try simply reading a book?\\nOr could you just not bear to look?\\nYou'll get no\\nYou'll get no\\nYou'll get no\\nYou'll get no\\nYou'll get no commercials\\nOompa Loompa Doompadee Dah\\nIf you're like reading you will go far\\nYou will live in happiness too\\nLike the Oompa\\nOompa Loompa doompadee do\\nOompa Loompas:\\nOompa Loompa doompadee doo\\nI've got another puzzle for you\\nOompa Loompa doompadah dee\\nIf you are wise you'll listen to me\\nWhat do you get from a glut of TV?\\nA pain in the neck and an IQ of three\\nWhy don't you try simply reading a book?\\nOr could you just not bear to look?\\nYou'll get no\\nYou'll get no\\nYou'll get no\\nYou'll get no\\nYou'll get no commercials\\nOompa Loompa Doompadee Dah\\nIf you're like reading you will go far\\nYou will live in happiness too\\nLike the Oompa\\nOompa Loompa doompadee do\\nOompa Loompas:\\nOompa Loompa doompadee doo\\nI've got another puzzle for you\\nOompa Loompa doompadah dee\\nIf you are wise you'll listen to me\\nWhat do you get from a glut of TV?\\nA pain in the neck and an IQ of three\\nWhy don't you try simply reading a book?\\nOr could you just not bear to look?\\nYou'll get no\\nYou'll get no\\nYou'll get no\\nYou'll get no\\nYou'll get no commercials\\nOompa Loompa Doompadee Dah\\nIf you're like reading you will go far\\nYou will live in happiness too\\nLike the Oompa\\nOompa Loompa doompadee do\\nOompa Loompas:\\nOompa Loompa doompadee doo\\nI've got another puzzle for you\\nOompa Loompa doompadah dee\\nIf you are wise you'll listen to me\\nWhat do you get from a glut of TV?\\nA pain in the neck and an IQ of three\\nWhy don't you try simply reading a book?\\nOr could you just not bear to look?\\nYou'll get no\\nYou'll get no\\nYou'll get no\\nYou'll get no\\nYou'll get no commercials\\nOompa Loompa Doompadee Dah\\nIf you're like reading you will go far\\nYou will live in happiness too\\nLike the Oompa\\nOompa Loompa doompadee do\"\n" +
            "      },\n" +
            "      \"gender\": \"F\",\n" +
            "      \"image\": \"https://s3.eu-central-1.amazonaws.com/napptilus/level-test/1.jpg\",\n" +
            "      \"profession\": \"Developer\",\n" +
            "      \"email\": \"mkaradzas1@visualengin.com\",\n" +
            "      \"age\": 21,\n" +
            "      \"country\": \"Loompalandia\",\n" +
            "      \"height\": 99,\n" +
            "      \"id\": 1\n" +
            "    }", OompaLoompa::class.java)
    LoompaTheme {
        OompaLoompaCard(oompaLoompa = oompaLoompa)
    }
}