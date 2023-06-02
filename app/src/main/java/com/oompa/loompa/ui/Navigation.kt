package com.oompa.loompa.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.oompa.loompa.viewmodel.OompaLoompaViewModel2

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "main",
    oompaLoompaViewModel: OompaLoompaViewModel2 = hiltViewModel(),
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("main") {
            MainScreen(
                oompaLoompaViewModel = oompaLoompaViewModel,
                onNavigateToOompaLoompaDetails = {
                    navController.navigate("oompaLoompaDetails/$it")
                }
            )
        }
        composable(
            "oompaLoompaDetails/{oompaLoompaId}",
            arguments = listOf(navArgument("oompaLoompaId") { type = NavType.LongType }),
        ) { backStackEntry ->
            OompaLoompaDetailsScreen(
                backStackEntry.arguments?.getLong("oompaLoompaId"),
            )
        }
    }
}