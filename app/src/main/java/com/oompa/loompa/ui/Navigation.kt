package com.oompa.loompa.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.oompa.loompa.viewmodel.DetailsScreenViewModel
import com.oompa.loompa.viewmodel.MainScreenViewModel

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "main",
    mainScreenViewModel: MainScreenViewModel = hiltViewModel(),
    detailsScreenViewModel: DetailsScreenViewModel = hiltViewModel(),
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(
            route = "main"
        ) {
            MainScreen(
                mainScreenViewModel = mainScreenViewModel,
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
                detailsScreenViewModel = detailsScreenViewModel,
                oompaLoompaId = navBackStackEntry.arguments?.getLong("oompaLoompaId"),
            )
        }
    }
}