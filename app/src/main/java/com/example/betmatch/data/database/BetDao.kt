package com.example.betmatch.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BetDao {

    @Query("SELECT * FROM bets ORDER BY id DESC")
    fun getAllBets(): Flow<List<BetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBet(bet: BetEntity)

    @Update
    suspend fun updateBet(bet: BetEntity)

    @Delete
    suspend fun deleteBet(bet: BetEntity)
}