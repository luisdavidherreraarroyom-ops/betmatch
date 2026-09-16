package com.example.betmatch.core.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.betmatch.data.database.MatchEntity

@Composable
fun CreateMatchScreen(
    existingMatch: MatchEntity? = null,
    onSaveClick: (participantA: String, participantB: String, durationInSeconds: Long) -> Unit = { _, _, _ -> },
    onBackClick: () -> Unit = {}
) {
    var participantA by remember { mutableStateOf(existingMatch?.participantA ?: "") }
    var participantB by remember { mutableStateOf(existingMatch?.participantB ?: "") }
    var durationText by remember { mutableStateOf(existingMatch?.durationInSeconds?.toString() ?: "") }

    val isEditing = existingMatch != null

    Scaffold { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("⚔️", style = MaterialTheme.typography.displayLarge)
            Text(
                if (isEditing) "Editar Enfrentamiento" else "Nuevo Enfrentamiento",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = participantA,
                onValueChange = { participantA = it },
                label = { Text("Participante A") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = participantB,
                onValueChange = { participantB = it },
                label = { Text("Participante B") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = durationText,
                onValueChange = { durationText = it.filter { c -> c.isDigit() } },
                label = { Text("Duración (segundos)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val duration = durationText.toLongOrNull() ?: 0L
                    onSaveClick(participantA, participantB, duration)
                    onBackClick()
                },
                enabled = participantA.isNotBlank() && participantB.isNotBlank() && durationText.isNotBlank()
            ) {
                Text(if (isEditing) "Guardar Cambios" else "Guardar y Volver")
            }
        }
    }
}