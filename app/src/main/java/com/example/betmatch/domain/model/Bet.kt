package com.example.betmatch.domain.model

data class Bet(
    val id: String,
    val matchId: String,
    val userId: String,
    val backedParticipant: String,
    val amount: Double
)