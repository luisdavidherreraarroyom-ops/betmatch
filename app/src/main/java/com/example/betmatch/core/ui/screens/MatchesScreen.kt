package com.example.betmatch.core.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.betmatch.data.database.MatchEntity

@Composable
fun MatchesScreen(
    matches: List<MatchEntity> = emptyList(),
    onCreateMatchClick: () -> Unit = {},
    onMatchClick: (Long) -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateMatchClick) {
                Text("+")
            }
        }
    ) { padding ->
        if (matches.isEmpty()) {
            Column(
                modifier = Modifier.padding(padding).fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("⚔️", style = MaterialTheme.typography.displayLarge)
                Text("Aún no hay partidos", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onCreateMatchClick) { Text("Crear Enfrentamiento") }
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(matches) { match ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onMatchClick(match.id) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "${match.participantA} vs ${match.participantB}",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text("Estado: ${match.status}", style = MaterialTheme.typography.bodyMedium)
                            Text("Duración: ${match.durationInSeconds}s", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}