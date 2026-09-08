package com.example.betmatch.core.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppDestinations(
    val label: String,
    val icon: ImageVector
) {
    TOURNAMENTS("Torneos", Icons.Default.EmojiEvents),
    MATCHES("Partidos", Icons.Default.DateRange),
    BETS("Apuestas", Icons.Default.ShoppingCart),
    PROFILE("Perfil", Icons.Default.AccountCircle)
}