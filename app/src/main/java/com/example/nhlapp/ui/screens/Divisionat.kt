package com.example.nhlapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nhlapp.ui.viewmodel.StandingsUIState
import com.example.nhlapp.ui.viewmodel.TeamGamesUIState
import com.example.nhlapp.ui.viewmodel.TeamGamesViewModel
import androidx.compose.ui.res.stringResource
import com.example.nhlapp.R

@Composable
fun Divisionat(
    uiState: StandingsUIState,
    teamGamesViewModel: TeamGamesViewModel,
    teamGameState: TeamGamesUIState,
    navController: NavHostController
) {
    var divisionaYla by remember { mutableStateOf("Central") }
    var divisionaAla by remember { mutableStateOf("Pacific") }

    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(4.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "$divisionaYla",
            modifier = Modifier.padding(8.dp),
            fontSize = 20.sp
        )
        when (uiState) {
            is StandingsUIState.Loading -> LoadingScreen()
            is StandingsUIState.Error -> ErrorScreen()
            is StandingsUIState.Success -> KokoLiiga(
                uiState.standings,
                filtter = divisionaYla,
                teamGameState,
                teamGamesViewModel,
                navController
            )
        }

        Text(
            text = "$divisionaAla",
            modifier = Modifier.padding(8.dp),
            fontSize = 20.sp
        )
        when (uiState) {
            is StandingsUIState.Loading -> LoadingScreen()
            is StandingsUIState.Error -> ErrorScreen()
            is StandingsUIState.Success -> KokoLiiga(
                uiState.standings,
                filtter =  divisionaAla,
                teamGameState,
                teamGamesViewModel,
                navController
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), // Padding to separate buttons from content below
            horizontalArrangement = Arrangement.SpaceEvenly // Center buttons with space between
        ) {
            Button(onClick = {
                divisionaYla = "Central"
                divisionaAla = "Pacific"
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White)
                ) {
                Text(text = "Läntiset Divisionat")
            }

            Button(onClick = {
                divisionaYla = "Atlantic"
                divisionaAla = "Metropolitan"
            }, colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White )
            ) {
                Text(text = "Itäiset Divisionat")
            }
        }
    }
}