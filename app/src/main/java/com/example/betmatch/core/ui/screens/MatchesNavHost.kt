package com.example.betmatch.core.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.betmatch.core.ui.CreateMatchRoute
import com.example.betmatch.core.ui.MatchDetailRoute
import com.example.betmatch.core.ui.MatchesRoute
import com.example.betmatch.features.matches.MatchViewModel
import androidx.navigation.toRoute

@Composable
fun MatchesNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModel: MatchViewModel = viewModel(
        factory = MatchViewModel.provideFactory(context)
    )
    val matches by viewModel.matches.collectAsState()

    NavHost(
        navController = navController,
        startDestination = MatchesRoute
    ) {
        composable<MatchesRoute> {
            MatchesScreen(
                matches = matches,
                onCreateMatchClick = { navController.navigate(CreateMatchRoute) },
                onMatchClick = { matchId -> navController.navigate(MatchDetailRoute(matchId)) }
            )
        }
        composable<CreateMatchRoute> {
            CreateMatchScreen(
                onSaveClick = { participantA, participantB, duration ->
                    viewModel.addMatch(participantA, participantB, duration)
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<MatchDetailRoute> { backStackEntry ->
            val route: MatchDetailRoute = backStackEntry.toRoute()
            val match = matches.find { it.id == route.matchId }
            MatchDetailScreen(
                match = match,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}