package com.example.betmatch.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tournaments")
data class TournamentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val rules: String,
    val maxPlayers: Int,
    val isCompleted: Boolean = false
)