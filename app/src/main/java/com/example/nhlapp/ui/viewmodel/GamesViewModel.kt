package com.example.nhlapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhlapp.ui.model.GameWeekResponse
import com.example.nhlapp.ui.model.GamesApi
import kotlinx.coroutines.launch

sealed interface GamesUIState {
    data class Success(val games: GameWeekResponse): GamesUIState
    object Error: GamesUIState
    object Loading: GamesUIState
}

class GamesViewModel: ViewModel() {
    var gamesUIState: GamesUIState by mutableStateOf(GamesUIState.Loading)
        private set

    var currentDate by mutableStateOf("now")
        private set

    init {
        getGamesList(currentDate)
    }

    fun getGamesList(date: String) {
        viewModelScope.launch {
            val gamesApi = GamesApi.getInstanceGames()
            gamesUIState = GamesUIState.Loading
            try {
                val response = gamesApi.getGames(date)
                gamesUIState = GamesUIState.Success(response)
                currentDate = response.currentDate // Update current date
            } catch (e: Exception) {
                Log.d("GamesViewModel", e.message.toString())
                gamesUIState = GamesUIState.Error
            }
        }
    }

    fun loadPreviousDate() {
        if (gamesUIState is GamesUIState.Success) {
            val prevDate = (gamesUIState as GamesUIState.Success).games.prevDate
            getGamesList(prevDate )
        }
    }

    fun loadNextDate() {
        if (gamesUIState is GamesUIState.Success) {
            val nextDate = (gamesUIState as GamesUIState.Success).games.nextDate
            getGamesList(nextDate)
        }
    }
}