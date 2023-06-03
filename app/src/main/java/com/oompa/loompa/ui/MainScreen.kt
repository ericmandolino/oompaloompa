package com.oompa.loompa.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oompa.loompa.R
import com.oompa.loompa.data.service.API_GENDER_FEMALE
import com.oompa.loompa.data.service.API_GENDER_MALE
import com.oompa.loompa.ui.theme.LoompaTheme
import com.oompa.loompa.viewmodel.OompaLoompaFilteringState
import com.oompa.loompa.viewmodel.OompaLoompaViewModel

@Composable
fun MainScreen(
    oompaLoompaViewModel: OompaLoompaViewModel,
    onNavigateToOompaLoompaDetails: (oompaLoompaId: Long) -> Unit
) {
    val availableProfessions = oompaLoompaViewModel.getProfessions().collectAsState(initial = listOf())
    Scaffold(
        modifier = Modifier.padding(8.dp),
        content = { paddingValues ->  MainScreenContent(oompaLoompaViewModel, onNavigateToOompaLoompaDetails, paddingValues) },
        floatingActionButton = {
            FilterComponent(
                oompaLoompaViewModel.getFilteringState(),
                availableProfessions.value,
            )
        },
    )
}

@Composable
fun MainScreenContent(
    oompaLoompaViewModel: OompaLoompaViewModel,
    onNavigateToOompaLoompaDetails: (oompaLoompaId: Long) -> Unit,
    paddingValues: PaddingValues,
) {
    PagingOompaLoompas(oompaLoompaViewModel, onNavigateToOompaLoompaDetails, paddingValues)
}

@Composable
fun FilterComponent(
    filteringState: OompaLoompaFilteringState,
    availableProfessions: List<String>,
) {
    var showFiltering by rememberSaveable { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (showFiltering) {
            FilterContent(
                filteringState,
                availableProfessions,
            )
        }
        FloatingActionButton(
            shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 50)),
            containerColor = MaterialTheme.colorScheme.secondary,
            onClick = { showFiltering = !showFiltering }
        ) {
            Icon(imageVector = Icons.Default.FilterAlt, contentDescription = stringResource(R.string.filter))
        }
    }
}

@Composable
fun FilterContent(
    filteringState: OompaLoompaFilteringState,
    availableProfessions: List<String>,
) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .width(240.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            FilterSection(filterName = stringResource(R.string.gender)) {
                FilterByGender(
                    filteringState.selectedGenders,
                    filteringState.onGenderFilterChanged
                )
            }
            FilterSection(filterName = stringResource(R.string.profession)) {
                FilterByProfession(
                    availableProfessions,
                    filteringState.selectedProfessions,
                    filteringState.onProfessionFilterChanged
                )
            }
        }
    }
}

@Composable
fun FilterSection(filterName: String, sectionContent: @Composable () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(text = filterName, fontSize = 14.sp)
        sectionContent()
    }
}

@Composable
fun FilterByGender(
    selectedGenders: List<String>,
    onGenderFilterChanged: (List<String>) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        TextRadioButton(
            isSelected = API_GENDER_MALE in selectedGenders,
            text = stringResource(R.string.male),
        ) {
            onGenderFilterChanged(listOf(API_GENDER_MALE))
        }
        TextRadioButton(
            isSelected = API_GENDER_FEMALE in selectedGenders,
            text = stringResource(R.string.female),
        ) {
            onGenderFilterChanged(listOf(API_GENDER_FEMALE))
        }
        TextRadioButton(
            isSelected = selectedGenders.isEmpty(),
            text = stringResource(R.string.both),
        ) {
            onGenderFilterChanged(listOf())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterByProfession(
    availableProfessions: List<String>,
    selectedProfessions: List<String>,
    onProfessionFilterChanged: (List<String>) -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            value = (
                    if (selectedProfessions.isEmpty()) stringResource(R.string.any)
                    else selectedProfessions.joinToString()
                    ),
            textStyle = TextStyle(
                fontStyle = (
                        if (selectedProfessions.isEmpty()) FontStyle.Italic
                        else FontStyle.Normal
                        ),
            ),
            singleLine = true,
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            availableProfessions.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onProfessionFilterChanged(
                            if (selectionOption in selectedProfessions) {
                                selectedProfessions.filter { profession -> profession != selectionOption }
                            } else {
                                selectedProfessions + selectionOption
                            }
                        )
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    modifier = Modifier
                        .background(
                            color =
                            if (selectionOption in selectedProfessions) MaterialTheme.colorScheme.secondary
                            else Color.Transparent
                        ),
                )
            }
        }
    }
}

@Composable
fun TextRadioButton(isSelected: Boolean, text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        RadioButton(selected = isSelected, onClick = onClick)
        Text(text = text, fontSize = 12.sp)
    }
}

@Preview(showBackground = true, widthDp = 240)
@Composable
fun FilterContentPreview() {
    LoompaTheme {
        FilterContent(
            filteringState = OompaLoompaFilteringState(
                selectedGenders = listOf(),
                onGenderFilterChanged = {},
                selectedProfessions = listOf ("Profession1"),
                onProfessionFilterChanged = {},
            ),
            availableProfessions = listOf ("Profession1", "Profession2"),
        )
    }
}