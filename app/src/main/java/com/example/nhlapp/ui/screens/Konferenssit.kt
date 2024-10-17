package com.example.nhlapp.ui.screens

import androidx.compose.foundation.layout.*
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
fun Konferenssit(
    uiState: StandingsUIState,
    teamGamesViewModel: TeamGamesViewModel,
    teamGameState: TeamGamesUIState,
    navController: NavHostController
) {
    var konferenssi by remember { mutableStateOf("Western") }

    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(4.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally) {
        val konferenssiSuomeksi = when(konferenssi) {
            "Western" -> stringResource(R.string.west_conf)
            "Eastern" -> stringResource(R.string.east_conf)
            else -> stringResource(R.string.unknown_conf)
        }
        Text(
            text = "$konferenssiSuomeksi",
            modifier = Modifier.padding(8.dp),
            fontSize = 20.sp,
        )
        when (uiState) {
            is StandingsUIState.Loading -> LoadingScreen()
            is StandingsUIState.Error -> ErrorScreen()
            is StandingsUIState.Success -> KokoLiiga(
                uiState.standings,
                filtter = konferenssi,
                teamGameState,
                teamGamesViewModel,
                navController
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), // Padding at the bottom for better spacing
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                onClick = { konferenssi = "Western" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.west_conf))
            }
            Button(
                onClick = { konferenssi = "Eastern" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.east_conf))
            }
        }
    }
}
