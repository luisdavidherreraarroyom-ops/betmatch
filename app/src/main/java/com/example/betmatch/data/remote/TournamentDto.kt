package com.example.betmatch.data.remote

import com.google.gson.annotations.SerializedName

data class TournamentDto(
    val id: String? = null,
    val title: String,
    val rules: String,
    val maxPlayers: Int,
    val isCompleted: Boolean = false
)