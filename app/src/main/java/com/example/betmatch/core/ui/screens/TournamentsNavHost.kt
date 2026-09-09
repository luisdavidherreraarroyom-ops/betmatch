package com.example.betmatch.core.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.betmatch.core.ui.CreateTournamentRoute
import com.example.betmatch.core.ui.TournamentsRoute

@Composable
fun TournamentsNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TournamentsRoute
    ) {
        composable<TournamentsRoute> {
            TournamentsScreen(
                onCreateTournamentClick = { navController.navigate(CreateTournamentRoute) }
            )
        }
        composable<CreateTournamentRoute> {
            CreateTournamentScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}