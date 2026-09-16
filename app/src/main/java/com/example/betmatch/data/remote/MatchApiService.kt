package com.example.betmatch.data.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MatchApiService {

    @GET("matches")
    suspend fun getMatches(): List<MatchDto>

    @POST("matches")
    suspend fun createMatch(@Body match: MatchDto): MatchDto

    @PUT("matches/{id}")
    suspend fun updateMatch(@Path("id") id: String, @Body match: MatchDto): MatchDto

    @DELETE("matches/{id}")
    suspend fun deleteMatch(@Path("id") id: String)
}