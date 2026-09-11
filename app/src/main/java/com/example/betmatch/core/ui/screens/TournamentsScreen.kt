package com.example.betmatch.core.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.betmatch.data.database.TournamentEntity

@Composable
fun TournamentsScreen(
    tournaments: List<TournamentEntity> = emptyList(),
    onCreateTournamentClick: () -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateTournamentClick) {
                Text("+")
            }
        }
    ) { padding ->
        if (tournaments.isEmpty()) {
            Column(
                modifier = Modifier.padding(padding).fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🏆", style = MaterialTheme.typography.displayLarge)
                Text("Aún no hay torneos", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onCreateTournamentClick) { Text("Crear Nuevo Torneo") }
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tournaments) { tournament ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(tournament.title, style = MaterialTheme.typography.titleLarge)
                            Text("Reglas: ${tournament.rules}", style = MaterialTheme.typography.bodyMedium)
                            Text("Máx. jugadores: ${tournament.maxPlayers}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}