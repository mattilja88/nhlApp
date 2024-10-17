package com.example.nhlapp.ui.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamGamesApi {
    @GET("club-schedule/{club}/month/{date}")
    suspend fun getTeamGames(@Path("date") date: String, @Path("club") club: String): Schedule

    companion object {
        private var gamesService: TeamGamesApi? = null

        fun getInstanceTeamGames(): TeamGamesApi {
            if (gamesService == null) {
                gamesService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TeamGamesApi::class.java)
            }
            return gamesService!!
        }
    }
}