package com.example.nhlapp.ui.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://api-web.nhle.com/v1/"

interface StandingsApi {
    @GET("standings/now")
    suspend fun getStandings(): Standings

    companion object {
        private var standingsService: StandingsApi? = null

        fun getInstance(): StandingsApi {
            if (standingsService == null) {
                standingsService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(StandingsApi::class.java)
            }
            return standingsService!!
        }
    }
}