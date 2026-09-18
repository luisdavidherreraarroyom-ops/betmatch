package com.example.betmatch.core.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.betmatch.features.bets.BetViewModel

@Composable
fun BetsNavHost() {
    val context = LocalContext.current
    val viewModel: BetViewModel = viewModel(
        factory = BetViewModel.provideFactory(context)
    )
    val bets by viewModel.bets.collectAsState()

    BetsScreen(
        bets = bets,
        onPlaceBet = { matchTitle, chosenOption, amount ->
            viewModel.placeBet(matchTitle, chosenOption, amount)
        },
        onUpdateBet = { bet, matchTitle, chosenOption, amount ->
            viewModel.updateBet(bet, matchTitle, chosenOption, amount)
        },
        onDeleteBet = { bet ->
            viewModel.deleteBet(bet)
        },
        onSetStatus = { bet, newStatus ->
            viewModel.setBetStatus(bet, newStatus)
        }
    )
}