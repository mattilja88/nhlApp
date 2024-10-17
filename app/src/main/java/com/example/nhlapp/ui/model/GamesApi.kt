package com.example.nhlapp.ui.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface GamesApi {
    @GET("score/{date}")
    suspend fun getGames(@Path("date") date: String): GameWeekResponse

    companion object {
        private var gamesService: GamesApi? = null

        fun getInstanceGames(): GamesApi {
            if (gamesService == null) {
                gamesService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GamesApi::class.java)
            }
            return gamesService!!
        }
    }
}