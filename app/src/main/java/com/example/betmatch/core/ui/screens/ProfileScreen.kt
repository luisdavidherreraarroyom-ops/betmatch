package com.example.betmatch.core.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.betmatch.data.database.UserProfileEntity

data class BetStats(
    val totalBets: Int,
    val totalAmount: Double,
    val pending: Int,
    val won: Int,
    val lost: Int
)

@Composable
fun ProfileScreen(
    profile: UserProfileEntity?,
    stats: BetStats,
    onSaveProfile: (username: String, email: String, balance: Double) -> Unit
) {
    if (profile == null) {
        ProfileCreationForm(onSaveProfile = onSaveProfile)
    } else {
        ProfileDashboard(profile = profile, stats = stats)
    }
}

@Composable
private fun ProfileCreationForm(
    onSaveProfile: (username: String, email: String, balance: Double) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var balanceText by remember { mutableStateOf("") }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("👤", style = MaterialTheme.typography.displayLarge)
            Text("Crea tu Perfil", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nombre de usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = balanceText,
                onValueChange = { balanceText = it },
                label = { Text("Saldo inicial") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val balance = balanceText.toDoubleOrNull()
                    if (username.isNotBlank() && email.isNotBlank() && balance != null) {
                        onSaveProfile(username, email, balance)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar y comenzar")
            }
        }
    }
}

@Composable
private fun ProfileDashboard(profile: UserProfileEntity, stats: BetStats) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("👤", style = MaterialTheme.typography.displayLarge)
            Text(profile.username, style = MaterialTheme.typography.headlineMedium)
            Text(profile.email, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(24.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Saldo actual: $${profile.balance}", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Estadísticas de Apuestas", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Total de apuestas: ${stats.totalBets}")
                    Text("Monto total apostado: $${stats.totalAmount}")
                    Text("Pendientes: ${stats.pending}")
                    Text("Ganadas: ${stats.won}")
                    Text("Perdidas: ${stats.lost}")
                }
            }
        }
    }
}