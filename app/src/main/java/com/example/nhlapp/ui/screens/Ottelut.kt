package com.example.nhlapp.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nhlapp.ui.model.GameWeekResponse
import com.example.nhlapp.ui.viewmodel.GamesUIState
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.nhlapp.R
import com.example.nhlapp.ui.model.Goal
import com.example.nhlapp.ui.model.TeamLeader
import com.example.nhlapp.ui.viewmodel.GamesViewModel
import coil.decode.SvgDecoder
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Ottelut(gameState:GamesUIState, viewModel: GamesViewModel) {

    when (gameState) {
        is GamesUIState.Loading -> LoadingScreen()
        is GamesUIState.Error -> ErrorScreen()
        is GamesUIState.Success -> OtteluLista(gameState.games, viewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OtteluLista(games: GameWeekResponse, gamesViewModel: GamesViewModel) {
    Column(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth()
    ) {
        // Top Row with navigation and date, centered
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { gamesViewModel.loadPreviousDate() },
                enabled = games.prevDate != null,
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "<--")
            }
            val date = games.currentDate
            val finnishDateStyle = convertToFinnishDate(currentDate = date)
            Text(
                text = finnishDateStyle,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Button(onClick = { gamesViewModel.loadNextDate() },
                enabled = games.nextDate != null,
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "-->")
            }
        }

        LazyColumn {
            items(games.games) { game ->
                var isExpanded by remember { mutableStateOf(false) }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .fillMaxWidth()
                        .clickable { isExpanded = !isExpanded },
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 1.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val time = game.startTimeUTC
                        val finnishTime = convertToFinnishTime(startTimeUTC = time)

                        Text(text = finnishTime)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(context = LocalContext.current)
                                    .data(game.homeTeam.logo)
                                    .decoderFactory(SvgDecoder.Factory())
                                    .crossfade(true)
                                    .build()
                            ),
                            contentDescription = "${game.homeTeam.name.default} logo",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(horizontal = 2.dp)
                        )
                        Text(
                            text = if (game.homeTeam.score != null && game.awayTeam.score != null) {
                                "${game.homeTeam.score}    -    ${game.awayTeam.score}"
                            } else {
                                "vs"
                            },
                            modifier = Modifier.padding(horizontal = 8.dp),
                            textAlign = TextAlign.Center
                        )
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(context = LocalContext.current)
                                    .data(game.awayTeam.logo)
                                    .decoderFactory(SvgDecoder.Factory())
                                    .crossfade(true)
                                    .build()
                            ),
                            contentDescription = "${game.awayTeam.name.default} logo",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(horizontal = 2.dp)
                        )
                    }
/*
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = game.homeTeam.name.default,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        Text(
                            text = game.awayTeam.name.default,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }*/
                }
                if (isExpanded) {
                    game.teamLeaders?.let { leaders ->
                        DisplayTeamLeaders(leaders)
                    }
                    game.goals?.let { goals ->
                        DisplayGoals(goals)
                    }
                }
                HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}

@Composable
fun DisplayTeamLeaders(teamLeaders: List<TeamLeader>) {
    Column(modifier = Modifier.padding(start = 64.dp)) {
        Text(text = "Team Leaders", fontWeight = FontWeight.Bold, color = Color.Gray)
        teamLeaders.forEach { leader ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(leader.headshot),
                    contentDescription = "${leader.firstName.default} ${leader.lastName.default}",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    val kategoria = when (leader.category) {
                        "goals" -> "Eniten maaleja"
                        "assists" -> "Eniten syöttöjä"
                        "wins" -> "Eniten voittoja"
                        else -> "Tuntematon kategoria"
                    }
                    Text(text = "${leader.firstName.default} ${leader.lastName.default} (${leader.teamAbbrev})", fontWeight = FontWeight.Bold)
                    Text(text = "$kategoria: ${leader.value}")
                }
            }
        }
    }
}

@Composable
fun DisplayGoals(goals: List<Goal>) {
    Column(modifier = Modifier.padding(start = 64.dp)) {
        Text(text = "Goals", fontWeight = FontWeight.Bold, color = Color.Gray)
        goals.forEach { goal ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(goal.mugshot),
                    contentDescription = "${goal.firstName.default} ${goal.lastName.default}",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = "${goal.firstName.default} ${goal.lastName.default} (${goal.teamAbbrev})", fontWeight = FontWeight.Bold)
                    Text(text = "Erä: ${goal.period}, Aika: ${goal.timeInPeriod}")
                    goal.assists.forEach { assist ->
                        Text(text = "Syöttö: ${assist.name.default}")
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun convertToFinnishTime(startTimeUTC: String): String {
    val utcTime = ZonedDateTime.parse(startTimeUTC)
    val finnishZone = ZoneId.of("Europe/Helsinki")
    val finnishTime = utcTime.withZoneSameInstant(finnishZone)
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    return finnishTime.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun convertToFinnishDate(currentDate: String): String {
    val date = LocalDate.parse(currentDate)
    val nextDay = date.plusDays(1)
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return nextDay.format(formatter)
}

