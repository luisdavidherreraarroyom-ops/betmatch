package com.example.betmatch.data.repository

import com.example.betmatch.data.database.BetDao
import com.example.betmatch.data.database.BetEntity
import kotlinx.coroutines.flow.Flow

class BetRepository(private val betDao: BetDao) {

    val allBets: Flow<List<BetEntity>> = betDao.getAllBets()

    suspend fun insertBet(bet: BetEntity) {
        betDao.insertBet(bet)
    }

    suspend fun updateBet(bet: BetEntity) {
        betDao.updateBet(bet)
    }

    suspend fun deleteBet(bet: BetEntity) {
        betDao.deleteBet(bet)
    }
}