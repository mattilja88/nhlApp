package com.example.nhlapp.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nhlapp.ui.model.TeamStanding
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.nhlapp.ui.viewmodel.TeamGamesUIState
import com.example.nhlapp.ui.viewmodel.TeamGamesViewModel

@Composable
fun KokoLiiga(
    standings: List<TeamStanding>,
    filtter: String,
    teamGameState: TeamGamesUIState,
    teamGamesViewModel: TeamGamesViewModel,
    navController: NavHostController
) {

    val filteredStandings = when (filtter) {
        "nhl" -> standings
        "Pacific", "Central", "Atlantic", "Metropolitan" -> standings.filter { team ->
            team.divisionName == filtter
        }
        else -> standings.filter { team ->
            team.conferenceName == filtter
        }
    }

    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        item {
            Row(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Joukkue",
                    modifier = Modifier.weight(4.5f),
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "O",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "V",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "H",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "JO",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "P",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "TM",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "PM",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "ME",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                )
            }
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
        }

        items(filteredStandings) { team ->
            Row(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth()
                    .clickable {
                        teamGamesViewModel.getTeamGames(team.teamAbbrev.default, "now")
                        navController.navigate("TeamGames")
                        Log.d("Responsetest", team.teamAbbrev.default)
                    }
            ) {
                Text(
                    text = team.teamCommonName.default,
                    modifier = Modifier.weight(4.5f)
                )
                Text(
                    text = team.gamesPlayed.toString(),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
                Text(
                    text = team.wins.toString(),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
                Text(
                    text = team.losses.toString(),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
                Text(
                    text = team.otLosses.toString(),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
                Text(
                    text = team.points.toString(),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
                Text(
                    text = team.goalFor.toString(),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
                Text(
                    text = team.goalAgainst.toString(),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
                Text(
                    text = team.goalDifferential.toString(),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}





