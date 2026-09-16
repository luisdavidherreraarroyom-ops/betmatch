package com.example.betmatch.core.ui

import kotlinx.serialization.Serializable

@Serializable
data object TournamentsRoute

@Serializable
data object CreateTournamentRoute

@Serializable
data class EditTournamentRoute(val tournamentId: Long)

@Serializable
data object MatchesRoute

@Serializable
data object CreateMatchRoute

@Serializable
data class MatchDetailRoute(val matchId: Long)

@Serializable
data class EditMatchRoute(val matchId: Long)