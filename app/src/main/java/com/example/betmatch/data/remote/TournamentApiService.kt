package com.example.betmatch.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TournamentApiService {

    @GET("tournaments")
    suspend fun getTournaments(): List<TournamentDto>

    @POST("tournaments")
    suspend fun createTournament(@Body tournament: TournamentDto): TournamentDto
}