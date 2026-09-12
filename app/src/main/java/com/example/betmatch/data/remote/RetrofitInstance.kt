package com.example.betmatch.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://6aa3913de7ae868cdf7b097f.mockapi.io/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: TournamentApiService by lazy {
        retrofit.create(TournamentApiService::class.java)
    }

    val matchApi: MatchApiService by lazy {
        retrofit.create(MatchApiService::class.java)
    }
}