package com.example.betmatch.core.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.betmatch.features.bets.BetViewModel
import com.example.betmatch.features.profile.ProfileViewModel

@Composable
fun ProfileNavHost() {
    val context = LocalContext.current

    val profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModel.provideFactory(context)
    )
    val betViewModel: BetViewModel = viewModel(
        factory = BetViewModel.provideFactory(context)
    )

    val profile by profileViewModel.profile.collectAsState()
    val bets by betViewModel.bets.collectAsState()

    val stats = BetStats(
        totalBets = bets.size,
        totalAmount = bets.sumOf { it.amount },
        pending = bets.count { it.status == "Pendiente" },
        won = bets.count { it.status == "Ganada" },
        lost = bets.count { it.status == "Perdida" }
    )

    ProfileScreen(
        profile = profile,
        stats = stats,
        onSaveProfile = { username, email, balance ->
            profileViewModel.saveProfile(username, email, balance)
        }
    )
}