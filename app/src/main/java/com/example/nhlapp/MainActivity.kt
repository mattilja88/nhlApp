package com.example.nhlapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nhlapp.ui.components.TabItem
import com.example.nhlapp.ui.theme.NhlAppTheme
import com.example.nhlapp.ui.screens.*
import com.example.nhlapp.ui.viewmodel.GamesUIState
import com.example.nhlapp.ui.viewmodel.GamesViewModel
import com.example.nhlapp.ui.viewmodel.StandingsViewModel
import com.example.nhlapp.ui.viewmodel.StandingsUIState
import com.example.nhlapp.ui.viewmodel.TeamGamesUIState
import com.example.nhlapp.ui.viewmodel.TeamGamesViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NhlAppTheme {
                NhlApp()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NhlApp(standingsViewModel: StandingsViewModel = viewModel(),
           gamesViewModel: GamesViewModel= viewModel(),
           teamGamesViewModel: TeamGamesViewModel = viewModel()){
    val items = listOf(
        TabItem(stringResource(R.string.home), Icons.Filled.Home, "Home"),
        TabItem(stringResource(R.string.conferences), Icons.Filled.Menu, "Konferenssit"),
        TabItem(stringResource(R.string.divisions), Icons.Filled.Menu, "Divisionat"),
        TabItem(stringResource(R.string.games), Icons.Filled.DateRange, "Ottelut")
    )
    BasicLayout(items, standingsViewModel, gamesViewModel, teamGamesViewModel)
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicLayout(
    items: List<TabItem>,
    standingsViewModel: StandingsViewModel,
    gamesViewModel: GamesViewModel,
    teamGamesViewModel: TeamGamesViewModel
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            NHLTopAppBar(
                title = (stringResource(R.string.app_title)),
                navController = navController
            )
        },
        bottomBar = {
            MyBottomNavigation(items, navController)
        },
    ) { paddingValues ->
        MyNavController(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            uiState = standingsViewModel.standingsUIState,
            gameState = gamesViewModel.gamesUIState,
            gamesViewModel = gamesViewModel,
            teamGamesViewModel = teamGamesViewModel,
            teamGameState = teamGamesViewModel.teamGamesUIState
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavController(
    navController: NavHostController,
    modifier: Modifier,
    uiState: StandingsUIState,
    gameState: GamesUIState,
    gamesViewModel: GamesViewModel,
    teamGamesViewModel: TeamGamesViewModel,
    teamGameState: TeamGamesUIState
) {
    NavHost(
        navController = navController,
        startDestination = "Home",
        modifier = modifier
    ) {
        composable(route = "Home") {
            NHL(uiState, teamGamesViewModel, teamGameState, navController)
        }
        composable(route = "Konferenssit") {
            Konferenssit(uiState,teamGamesViewModel, teamGameState, navController)
        }
        composable(route = "Divisionat") {
            Divisionat(uiState,teamGamesViewModel, teamGameState, navController)
        }
        composable(route = "Ottelut") {
            Ottelut(gameState, gamesViewModel)
        }
        composable(route = "TeamGames") {
            TeamGames(teamGameState, teamGamesViewModel)
        }
    }
}

@Composable
fun MyBottomNavigation(items: List<TabItem>, navController: NavController) {
    var selectedItem by remember { mutableStateOf(0) }
    NavigationBar (containerColor = Color.DarkGray, contentColor = Color.White) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route)
                },
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFC4CED4),       // Color for selected icon
                    unselectedIconColor = Color.White,      // Color for unselected icon
                    selectedTextColor = Color(0xFFC4CED4),       // Color for selected text
                    unselectedTextColor = Color.White,      // Color for unselected text
                    indicatorColor = Color.Black            // Color for the indicator
                )
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NhlAppTheme {
        NhlApp()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NHLTopAppBar(
    title: String,
    navController: NavController,
) {
    var isTeamGamesPage by remember { mutableStateOf(false) }

    // Track route changes
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            isTeamGamesPage = destination.route == "TeamGames"
        }
    }

    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    text = title,
                    color = Color(0xFFC4CED4)
                )
            }
        },
        navigationIcon = {
            if (isTeamGamesPage) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = Color(0xFFC4CED4)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.DarkGray
        ),
    )
}

