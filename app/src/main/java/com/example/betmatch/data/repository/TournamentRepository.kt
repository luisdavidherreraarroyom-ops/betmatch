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
        val localId = dao.insertTournament(tournament)

        try {
            val created = RetrofitInstance.api.createTournament(
                TournamentDto(
                    title = tournament.title,
                    rules = tournament.rules,
                    maxPlayers = tournament.maxPlayers,
                    isCompleted = tournament.isCompleted
                )
            )
            // Guardamos el id que nos dio la API, vinculado al registro local
            dao.updateTournament(
                tournament.copy(id = localId, remoteId = created.id)
            )
            Log.d("BetMatchAPI", "Torneo enviado a la API correctamente")
        } catch (e: Exception) {
            Log.e("BetMatchAPI", "Error al enviar torneo a la API", e)
        }
    }

    suspend fun updateTournament(tournament: TournamentEntity) {
        dao.updateTournament(tournament)

        val remoteId = tournament.remoteId
        if (remoteId != null) {
            try {
                RetrofitInstance.api.updateTournament(
                    remoteId,
                    TournamentDto(
                        id = remoteId,
                        title = tournament.title,
                        rules = tournament.rules,
                        maxPlayers = tournament.maxPlayers,
                        isCompleted = tournament.isCompleted
                    )
                )
                Log.d("BetMatchAPI", "Torneo actualizado en la API correctamente")
            } catch (e: Exception) {
                Log.e("BetMatchAPI", "Error al actualizar torneo en la API", e)
            }
        }
    }

    suspend fun deleteTournament(tournament: TournamentEntity) {
        dao.deleteTournament(tournament)

        val remoteId = tournament.remoteId
        if (remoteId != null) {
            try {
                RetrofitInstance.api.deleteTournament(remoteId)
                Log.d("BetMatchAPI", "Torneo eliminado de la API correctamente")
            } catch (e: Exception) {
                Log.e("BetMatchAPI", "Error al eliminar torneo de la API", e)
            }
        }
    }

    suspend fun refreshTournaments() {
        try {
            val remoteTournaments = RetrofitInstance.api.getTournaments()
            Log.d("BetMatchAPI", "Torneos recibidos de la API: ${remoteTournaments.size}")
            remoteTournaments.forEach { dto ->
                val remoteId = dto.id ?: return@forEach
                val existing = dao.getByRemoteId(remoteId)
                if (existing == null) {
                    dao.insertTournament(
                        TournamentEntity(
                            remoteId = remoteId,
                            title = dto.title,
                            rules = dto.rules,
                            maxPlayers = dto.maxPlayers,
                            isCompleted = dto.isCompleted
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("BetMatchAPI", "Error al traer torneos de la API", e)
        }
    }
}