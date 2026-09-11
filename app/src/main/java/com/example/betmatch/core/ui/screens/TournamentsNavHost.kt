package com.example.betmatch.core.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.betmatch.core.ui.CreateTournamentRoute
import com.example.betmatch.core.ui.TournamentsRoute
import com.example.betmatch.features.tournaments.TournamentViewModel

@Composable
fun TournamentsNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModel: TournamentViewModel = viewModel(
        factory = TournamentViewModel.provideFactory(context)
    )
    val tournaments by viewModel.tournaments.collectAsState()

    NavHost(
        navController = navController,
        startDestination = TournamentsRoute
    ) {
        composable<TournamentsRoute> {
            TournamentsScreen(
                tournaments = tournaments,
                onCreateTournamentClick = { navController.navigate(CreateTournamentRoute) }
            )
        }
        composable<CreateTournamentRoute> {
            CreateTournamentScreen(
                onSaveClick = { title, rules, maxPlayers ->
                    viewModel.addTournament(title, rules, maxPlayers)
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}