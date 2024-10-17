package com.example.nhlapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhlapp.ui.model.GameWeekResponse
import com.example.nhlapp.ui.model.GamesApi
import com.example.nhlapp.ui.model.Schedule
import com.example.nhlapp.ui.model.TeamGamesApi
import kotlinx.coroutines.launch

sealed interface TeamGamesUIState {
    data class Success(val teamGames: Schedule): TeamGamesUIState
    object Error: TeamGamesUIState
    object Loading: TeamGamesUIState
}

class TeamGamesViewModel: ViewModel() {
    var teamGamesUIState: TeamGamesUIState by mutableStateOf(TeamGamesUIState.Loading)
        private set

    var currentDate by mutableStateOf("now")
        private set

    var defaultTeam by mutableStateOf("CAR")
        private set

    init {
        getTeamGamesList(currentDate)
    }

    fun getTeamGamesList(date: String, team: String = defaultTeam) {
        viewModelScope.launch {
            val teamGamesApi = TeamGamesApi.getInstanceTeamGames()
            teamGamesUIState = TeamGamesUIState.Loading
            try {
                val response = teamGamesApi.getTeamGames(date, team)
                Log.d("Responsetest", response.toString())
                teamGamesUIState = TeamGamesUIState.Success(response)
            } catch (e: Exception) {
                Log.d("TeamGamesViewModel", e.message.toString())
                teamGamesUIState = TeamGamesUIState.Error
            }
        }
    }

    fun getTeamGames(teamName: String, date: String) {
        Log.d("TeamGamesViewModel", "Fetching games for $teamName on $date")
        currentDate = date  // Update currentDate with the new date
        defaultTeam = teamName  // Update defaultTeam with the new team name
        getTeamGamesList(date, teamName) // Call the main function
    }
}