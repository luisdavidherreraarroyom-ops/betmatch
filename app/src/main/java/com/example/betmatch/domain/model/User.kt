package com.example.betmatch.domain.model

data class User(
    val id: String,
    val username: String,
    val email: String,
    val balancePoints: Double = 1000.0
)