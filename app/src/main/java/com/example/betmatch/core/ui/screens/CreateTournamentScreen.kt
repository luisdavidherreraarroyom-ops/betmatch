package com.example.betmatch.core.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateTournamentScreen(
    onSaveClick: (title: String, rules: String, maxPlayers: Int) -> Unit = { _, _, _ -> },
    onBackClick: () -> Unit = {}
) {
    var title by remember { mutableStateOf("") }
    var rules by remember { mutableStateOf("") }
    var maxPlayersText by remember { mutableStateOf("") }

    Scaffold { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("⚙️", style = MaterialTheme.typography.displayLarge)
            Text("Configurar Torneo / Reglas Custom", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Nombre del torneo") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = rules,
                onValueChange = { rules = it },
                label = { Text("Reglas") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = maxPlayersText,
                onValueChange = { maxPlayersText = it.filter { c -> c.isDigit() } },
                label = { Text("Máximo de jugadores") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val maxPlayers = maxPlayersText.toIntOrNull() ?: 0
                    onSaveClick(title, rules, maxPlayers)
                    onBackClick()
                },
                enabled = title.isNotBlank() && rules.isNotBlank() && maxPlayersText.isNotBlank()
            ) {
                Text("Guardar y Volver")
            }
        }
    }
}