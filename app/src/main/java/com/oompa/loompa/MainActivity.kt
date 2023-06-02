package com.oompa.loompa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.oompa.loompa.ui.Navigation
import com.oompa.loompa.ui.theme.LoompaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoompaTheme {
                Navigation()
            }
        }
    }
}