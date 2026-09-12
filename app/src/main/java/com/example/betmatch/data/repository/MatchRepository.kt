package com.example.betmatch.data.repository

import android.util.Log
import com.example.betmatch.data.database.MatchDao
import com.example.betmatch.data.database.MatchEntity
import com.example.betmatch.data.remote.MatchDto
import com.example.betmatch.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class MatchRepository(private val dao: MatchDao) {

    val allMatches: Flow<List<MatchEntity>> = dao.getAllMatches()

    suspend fun addMatch(match: MatchEntity) {
        dao.insertMatch(match)

        try {
            RetrofitInstance.matchApi.createMatch(
                MatchDto(
                    tournamentId = match.tournamentId,
                    participantA = match.participantA,
                    participantB = match.participantB,
                    durationInSeconds = match.durationInSeconds,
                    totalBetPool = match.totalBetPool,
                    status = match.status,
                    winnerId = match.winnerId
                )
            )
            Log.d("BetMatchAPI", "Partido enviado a la API correctamente")
        } catch (e: Exception) {
            Log.e("BetMatchAPI", "Error al enviar partido a la API", e)
        }
    }

    suspend fun refreshMatches() {
        try {
            val remoteMatches = RetrofitInstance.matchApi.getMatches()
            Log.d("BetMatchAPI", "Partidos recibidos de la API: ${remoteMatches.size}")
            remoteMatches.forEach { dto ->
                dao.insertMatch(
                    MatchEntity(
                        tournamentId = dto.tournamentId,
                        participantA = dto.participantA,
                        participantB = dto.participantB,
                        durationInSeconds = dto.durationInSeconds,
                        totalBetPool = dto.totalBetPool,
                        status = dto.status,
                        winnerId = dto.winnerId
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("BetMatchAPI", "Error al traer partidos de la API", e)
        }
    }
}