package com.oompa.loompa.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.oompa.loompa.viewmodel.OompaLoompaViewModel

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "main",
    oompaLoompaViewModel: OompaLoompaViewModel = hiltViewModel(),
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(
            route = "main"
        ) {
            MainScreen(
                oompaLoompaViewModel = oompaLoompaViewModel,
                onNavigateToOompaLoompaDetails = {
                    navController.navigate("oompaLoompaDetails/$it")
                }
            )
        }
        composable(
            route = "oompaLoompaDetails/{oompaLoompaId}",
            arguments = listOf(navArgument("oompaLoompaId") { type = NavType.LongType }),
        ) { navBackStackEntry ->
            OompaLoompaDetailsScreen(
                oompaLoompaViewModel = oompaLoompaViewModel,
                oompaLoompaId = navBackStackEntry.arguments?.getLong("oompaLoompaId"),
            )
        }
    }
}