package com.example.betmatch.core.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.betmatch.data.database.MatchEntity

@Composable
fun MatchDetailScreen(
    match: MatchEntity?,
    onBackClick: () -> Unit = {}
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("⏱️", style = MaterialTheme.typography.displayLarge)
            Text("Detalles del Encuentro", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            if (match != null) {
                Text("${match.participantA} vs ${match.participantB}", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Estado: ${match.status}")
                Text("Duración: ${match.durationInSeconds} segundos")
                Text("Bolsa de apuestas: $${match.totalBetPool}")
            } else {
                Text("Partido no encontrado")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBackClick) {
                Text("Volver a Partidos")
            }
        }
    }
}