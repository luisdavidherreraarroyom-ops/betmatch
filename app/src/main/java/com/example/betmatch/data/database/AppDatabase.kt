package com.example.betmatch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TournamentEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tournamentDao(): TournamentDao
}