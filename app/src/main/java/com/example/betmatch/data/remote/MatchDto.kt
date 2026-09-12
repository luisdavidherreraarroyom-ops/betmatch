package com.example.betmatch.data.remote

data class MatchDto(
    val id: String? = null,
    val tournamentId: String? = null,
    val participantA: String,
    val participantB: String,
    val durationInSeconds: Long,
    val totalBetPool: Double = 0.0,
    val status: String = "PENDING",
    val winnerId: String? = null
)