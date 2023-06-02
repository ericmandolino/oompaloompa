package com.oompa.loompa.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun OompaLoompaDetailsScreen(oompaLoompaId: Long?) {
    Text(text = "Oompa Loompa! $oompaLoompaId")
}