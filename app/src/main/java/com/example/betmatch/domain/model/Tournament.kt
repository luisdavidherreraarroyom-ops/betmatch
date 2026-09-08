package com.example.betmatch.domain.model

data class Tournament(
    val id: String,
    val title: String,
    val discipline: String,
    val isCustomRules: Boolean,
    val customRulesDescription: String? = null,
    val creatorId: String,
    val status: TournamentStatus = TournamentStatus.CREATED
)

enum class TournamentStatus { CREATED, IN_PROGRESS, FINISHED }