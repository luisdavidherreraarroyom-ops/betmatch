package com.example.betmatch.domain.model

data class Match(
    val id: String,
    val tournamentId: String? = null,
    val participantA: String,
    val participantB: String,
    val durationInSeconds: Long,
    val totalBetPool: Double = 0.0,
    val status: MatchStatus = MatchStatus.PENDING,
    val winnerId: String? = null
)

enum class MatchStatus { PENDING, IN_PROGRESS, FINISHED }