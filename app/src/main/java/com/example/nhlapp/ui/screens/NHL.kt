package com.example.nhlapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.nhlapp.ui.viewmodel.StandingsUIState
import com.example.nhlapp.ui.viewmodel.TeamGamesUIState
import com.example.nhlapp.ui.viewmodel.TeamGamesViewModel


@Composable
fun NHL(
    uiState: StandingsUIState,
    teamGamesViewModel: TeamGamesViewModel,
    teamGameState: TeamGamesUIState,
    navController: NavHostController
){
    when (uiState) {
        is StandingsUIState.Loading -> LoadingScreen()
        is StandingsUIState.Error -> ErrorScreen()
        is StandingsUIState.Success -> KokoLiiga(uiState.standings, filtter = "nhl", teamGameState, teamGamesViewModel, navController)
    }
}



