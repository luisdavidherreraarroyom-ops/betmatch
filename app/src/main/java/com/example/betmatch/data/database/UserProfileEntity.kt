package com.example.betmatch.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Long = 1,
    val username: String,
    val email: String,
    val balance: Double
)