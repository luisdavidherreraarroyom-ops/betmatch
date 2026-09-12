package com.example.betmatch.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class MatchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tournamentId: String? = null,
    val participantA: String,
    val participantB: String,
    val durationInSeconds: Long,
    val totalBetPool: Double = 0.0,
    val status: String = "PENDING",
    val winnerId: String? = null
)