package com.example.nhlapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhlapp.ui.model.TeamStanding
import com.example.nhlapp.ui.model.StandingsApi
import kotlinx.coroutines.launch

sealed interface StandingsUIState {
    data class Success(val standings: List<TeamStanding>): StandingsUIState
    object Error: StandingsUIState
    object Loading: StandingsUIState
}

class StandingsViewModel: ViewModel() {
    var standingsUIState: StandingsUIState by mutableStateOf<StandingsUIState>(StandingsUIState.Loading)
        private set

    init {
        getStandingsList()
    }

    private fun getStandingsList() {
        viewModelScope.launch {
            var standingsApi: StandingsApi? = null
            try {
                standingsApi = StandingsApi.getInstance()
                standingsUIState = StandingsUIState.Success(standingsApi.getStandings().standings)
            } catch (e: Exception) {
                Log.d("STANDINGSVIEWMODEL", e.message.toString())
                standingsUIState = StandingsUIState.Error
            }
        }
    }
}