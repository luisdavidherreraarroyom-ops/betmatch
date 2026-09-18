package com.example.betmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.betmatch.core.ui.AppDestinations
import com.example.betmatch.core.ui.screens.BetsScreen
import com.example.betmatch.core.ui.screens.MatchesScreen
import com.example.betmatch.core.ui.screens.ProfileScreen
import com.example.betmatch.core.ui.screens.TournamentsNavHost
import com.example.betmatch.core.ui.screens.MatchesNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BetMatchApp()
                }
            }
        }
    }
}

@Composable
fun BetMatchApp() {
    var currentDestination by remember { mutableStateOf(AppDestinations.TOURNAMENTS) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach { destination ->
                item(
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = destination.label
                        )
                    },
                    label = { Text(destination.label) },
                    selected = destination == currentDestination,
                    onClick = { currentDestination = destination }
                )
            }
        }
    ) {
        when (currentDestination) {
            AppDestinations.TOURNAMENTS -> TournamentsNavHost()
            AppDestinations.MATCHES -> MatchesNavHost()
            AppDestinations.BETS -> BetsScreen()
            AppDestinations.PROFILE -> ProfileScreen()
        }
    }
}