package com.example.betmatch.data.repository

import android.util.Log
import com.example.betmatch.data.database.TournamentDao
import com.example.betmatch.data.database.TournamentEntity
import com.example.betmatch.data.remote.RetrofitInstance
import com.example.betmatch.data.remote.TournamentDto
import kotlinx.coroutines.flow.Flow

class TournamentRepository(private val dao: TournamentDao) {

    val allTournaments: Flow<List<TournamentEntity>> = dao.getAllTournaments()

    suspend fun addTournament(tournament: TournamentEntity) {
        dao.insertTournament(tournament)

        try {
            RetrofitInstance.api.createTournament(
                TournamentDto(
                    title = tournament.title,
                    rules = tournament.rules,
                    maxPlayers = tournament.maxPlayers,
                    isCompleted = tournament.isCompleted
                )
            )
            Log.d("BetMatchAPI", "Torneo enviado a la API correctamente")
        } catch (e: Exception) {
            Log.e("BetMatchAPI", "Error al enviar torneo a la API", e)
        }
    }

    suspend fun refreshTournaments() {
        try {
            val remoteTournaments = RetrofitInstance.api.getTournaments()
            Log.d("BetMatchAPI", "Torneos recibidos de la API: ${remoteTournaments.size}")
            remoteTournaments.forEach { dto ->
                dao.insertTournament(
                    TournamentEntity(
                        title = dto.title,
                        rules = dto.rules,
                        maxPlayers = dto.maxPlayers,
                        isCompleted = dto.isCompleted
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("BetMatchAPI", "Error al traer torneos de la API", e)
        }
    }
}