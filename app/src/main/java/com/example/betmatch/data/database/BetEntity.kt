package com.example.betmatch.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bets")
data class BetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val matchTitle: String,
    val chosenOption: String,
    val amount: Double,
    val status: String = "Pendiente"
)