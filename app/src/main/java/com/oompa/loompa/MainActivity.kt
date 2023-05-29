package com.oompa.loompa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.oompa.loompa.ui.PagingOompaLoompas
import com.oompa.loompa.ui.theme.LoompaTheme
import com.oompa.loompa.viewmodel.OompaLoompaViewModel2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoompaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: OompaLoompaViewModel2 = hiltViewModel()
                    PagingOompaLoompas(viewModel)
                }
            }
        }
    }
}