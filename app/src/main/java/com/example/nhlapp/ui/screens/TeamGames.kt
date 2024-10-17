package com.example.nhlapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.nhlapp.R
import com.example.nhlapp.ui.model.Schedule
import com.example.nhlapp.ui.viewmodel.TeamGamesUIState
import com.example.nhlapp.ui.viewmodel.TeamGamesViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TeamGames(teamGameState: TeamGamesUIState, teamGamesViewModel: TeamGamesViewModel) {
    when (teamGameState) {
        is TeamGamesUIState.Loading -> LoadingScreen()
        is TeamGamesUIState.Error -> ErrorScreen()
        is TeamGamesUIState.Success -> JoukkueenPelit(teamGameState.teamGames, teamGamesViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JoukkueenPelit(teamGames: Schedule, teamGamesViewModel: TeamGamesViewModel) {
    Column(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Text(text = stringResource(R.string.monthly_games),
                    modifier = Modifier
                        .padding(32.dp),
                    fontSize = 32.sp
                )
            }
            items(teamGames.games) { game ->
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
                            contentDescription = "${game.homeTeam.abbrev} logo",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(horizontal = 2.dp)
                        )
                        Text(
                            text = if (game.homeTeam.score != null && game.awayTeam.score != null) {
                                "${game.homeTeam.score}    -    ${game.awayTeam.score}"
                            } else {
                                stringResource(R.string.vs_text)
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
                            contentDescription = "${game.awayTeam.abbrev} logo",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(horizontal = 2.dp)
                        )
                    }
                }
                HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}
