package com.example.betmatch.data.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TournamentApiService {

    @GET("tournaments")
    suspend fun getTournaments(): List<TournamentDto>

    @POST("tournaments")
    suspend fun createTournament(@Body tournament: TournamentDto): TournamentDto

    @PUT("tournaments/{id}")
    suspend fun updateTournament(@Path("id") id: String, @Body tournament: TournamentDto): TournamentDto

    @DELETE("tournaments/{id}")
    suspend fun deleteTournament(@Path("id") id: String)
}