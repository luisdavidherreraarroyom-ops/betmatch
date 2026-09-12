package com.example.betmatch.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MatchApiService {

    @GET("matches")
    suspend fun getMatches(): List<MatchDto>

    @POST("matches")
    suspend fun createMatch(@Body match: MatchDto): MatchDto
}