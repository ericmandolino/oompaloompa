package com.oompa.loompa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.oompa.loompa.ui.MainScreen
import com.oompa.loompa.ui.theme.LoompaTheme
import com.oompa.loompa.viewmodel.OompaLoompaViewModel2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoompaTheme {
                val oompaLoompaViewModel: OompaLoompaViewModel2 = hiltViewModel()
                MainScreen(oompaLoompaViewModel)
            }
        }
    }
}