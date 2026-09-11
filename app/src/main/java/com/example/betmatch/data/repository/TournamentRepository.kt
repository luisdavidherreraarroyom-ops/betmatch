package com.example.betmatch.data.repository

import com.example.betmatch.data.database.TournamentDao
import com.example.betmatch.data.database.TournamentEntity
import kotlinx.coroutines.flow.Flow

class TournamentRepository(private val dao: TournamentDao) {

    val allTournaments: Flow<List<TournamentEntity>> = dao.getAllTournaments()

    suspend fun addTournament(tournament: TournamentEntity) {
        dao.insertTournament(tournament)
    }
}