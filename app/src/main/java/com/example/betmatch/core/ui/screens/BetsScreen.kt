package com.example.betmatch.core.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.betmatch.data.database.BetEntity

@Composable
fun BetsScreen(
    bets: List<BetEntity>,
    onPlaceBet: (matchTitle: String, chosenOption: String, amount: Double) -> Unit,
    onUpdateBet: (bet: BetEntity, matchTitle: String, chosenOption: String, amount: Double) -> Unit,
    onDeleteBet: (bet: BetEntity) -> Unit,
    onSetStatus: (bet: BetEntity, newStatus: String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var editingBet by remember { mutableStateOf<BetEntity?>(null) }

    Scaffold(
        floatingActionButton = {
            if (bets.isNotEmpty()) {
                FloatingActionButton(onClick = {
                    editingBet = null
                    showDialog = true
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "Crear apuesta")
                }
            }
        }
    ) { padding ->
        if (bets.isEmpty()) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🎲", style = MaterialTheme.typography.displayLarge)
                Text("Aún no hay apuestas", style = MaterialTheme.typography.headlineMedium)

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    editingBet = null
                    showDialog = true
                }) {
                    Text("Crear Apuesta")
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(bets) { bet ->
                    BetCard(
                        bet = bet,
                        onEditClick = {
                            editingBet = bet
                            showDialog = true
                        },
                        onDeleteClick = { onDeleteBet(bet) },
                        onSetStatus = { newStatus -> onSetStatus(bet, newStatus) }
                    )
                }
            }
        }
    }

    if (showDialog) {
        BetDialog(
            existingBet = editingBet,
            onDismiss = { showDialog = false },
            onConfirm = { matchTitle, chosenOption, amount ->
                if (editingBet != null) {
                    onUpdateBet(editingBet!!, matchTitle, chosenOption, amount)
                } else {
                    onPlaceBet(matchTitle, chosenOption, amount)
                }
                showDialog = false
            }
        )
    }
}

@Composable
private fun BetCard(
    bet: BetEntity,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onSetStatus: (String) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(bet.matchTitle, style = MaterialTheme.typography.titleMedium)
                    Text("Opción: ${bet.chosenOption}", style = MaterialTheme.typography.bodyMedium)
                    Text("Monto: $${bet.amount}", style = MaterialTheme.typography.bodyMedium)
                    Text("Estado: ${bet.status}", style = MaterialTheme.typography.bodySmall)
                }
                Row {
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Filled.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                    }
                }
            }

            if (bet.status == "Pendiente") {
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { onSetStatus("Ganada") }) {
                        Text("Ganada")
                    }
                    OutlinedButton(onClick = { onSetStatus("Perdida") }) {
                        Text("Perdida")
                    }
                }
            }
        }
    }
}

@Composable
private fun BetDialog(
    existingBet: BetEntity?,
    onDismiss: () -> Unit,
    onConfirm: (matchTitle: String, chosenOption: String, amount: Double) -> Unit
) {
    var matchTitle by remember { mutableStateOf(existingBet?.matchTitle ?: "") }
    var chosenOption by remember { mutableStateOf(existingBet?.chosenOption ?: "") }
    var amountText by remember { mutableStateOf(existingBet?.amount?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (existingBet != null) "Editar Apuesta" else "Nueva Apuesta") },
        text = {
            Column {
                OutlinedTextField(
                    value = matchTitle,
                    onValueChange = { matchTitle = it },
                    label = { Text("Partido") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = chosenOption,
                    onValueChange = { chosenOption = it },
                    label = { Text("Opción elegida") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("Monto") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val amount = amountText.toDoubleOrNull()
                if (matchTitle.isNotBlank() && chosenOption.isNotBlank() && amount != null) {
                    onConfirm(matchTitle, chosenOption, amount)
                }
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}