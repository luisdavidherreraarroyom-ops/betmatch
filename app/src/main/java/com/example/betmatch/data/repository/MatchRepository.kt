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
        val localId = dao.insertMatch(match)

        try {
            val created = RetrofitInstance.matchApi.createMatch(
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
            dao.updateMatch(match.copy(id = localId, remoteId = created.id))
            Log.d("BetMatchAPI", "Partido enviado a la API correctamente")
        } catch (e: Exception) {
            Log.e("BetMatchAPI", "Error al enviar partido a la API", e)
        }
    }

    suspend fun updateMatch(match: MatchEntity) {
        dao.updateMatch(match)

        val remoteId = match.remoteId
        if (remoteId != null) {
            try {
                RetrofitInstance.matchApi.updateMatch(
                    remoteId,
                    MatchDto(
                        id = remoteId,
                        tournamentId = match.tournamentId,
                        participantA = match.participantA,
                        participantB = match.participantB,
                        durationInSeconds = match.durationInSeconds,
                        totalBetPool = match.totalBetPool,
                        status = match.status,
                        winnerId = match.winnerId
                    )
                )
                Log.d("BetMatchAPI", "Partido actualizado en la API correctamente")
            } catch (e: Exception) {
                Log.e("BetMatchAPI", "Error al actualizar partido en la API", e)
            }
        }
    }

    suspend fun deleteMatch(match: MatchEntity) {
        dao.deleteMatch(match)

        val remoteId = match.remoteId
        if (remoteId != null) {
            try {
                RetrofitInstance.matchApi.deleteMatch(remoteId)
                Log.d("BetMatchAPI", "Partido eliminado de la API correctamente")
            } catch (e: Exception) {
                Log.e("BetMatchAPI", "Error al eliminar partido de la API", e)
            }
        }
    }

    suspend fun refreshMatches() {
        try {
            val remoteMatches = RetrofitInstance.matchApi.getMatches()
            Log.d("BetMatchAPI", "Partidos recibidos de la API: ${remoteMatches.size}")
            remoteMatches.forEach { dto ->
                val remoteId = dto.id ?: return@forEach
                val existing = dao.getByRemoteId(remoteId)
                if (existing == null) {
                    dao.insertMatch(
                        MatchEntity(
                            remoteId = remoteId,
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
            }
        } catch (e: Exception) {
            Log.e("BetMatchAPI", "Error al traer partidos de la API", e)
        }
    }
}