package com.example.betmatch.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://6aa3913de7ae868cdf7b097f.mockapi.io/"

    val api: TournamentApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TournamentApiService::class.java)
    }
}