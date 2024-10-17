package com.example.nhlapp.ui.model

import com.google.gson.annotations.SerializedName

data class GameWeekResponse(
    val prevDate: String,
    val currentDate: String,
    val nextDate: String,
    val gameWeek: List<GameDay>,
    val oddsPartners: List<OddsPartner>,
    val games: List<Game>
)

data class GameDay(
    val date: String,
    val dayAbbrev: String,
    val numberOfGames: Int
)

data class OddsPartner(
    val partnerId: Int,
    val country: String,
    val name: String,
    val imageUrl: String,
    val siteUrl: String? = null,  // Nullable to handle missing URLs
    val bgColor: String,
    val textColor: String,
    val accentColor: String
)

data class Game(
    val id: Long,
    val season: Long,
    val gameType: Int,
    val gameDate: String,
    val venue: Venue,
    val startTimeUTC: String,
    val easternUTCOffset: String,
    val venueUTCOffset: String,
    val tvBroadcasts: List<TvBroadcast>,
    val gameState: String,
    val gameScheduleState: String,
    val awayTeam: Team,
    val homeTeam: Team,
    val gameCenterLink: String,
    val neutralSite: Boolean,
    val venueTimezone: String,
    val ticketsLink: String? = null,
    val ticketsLinkFr: String? = null,
    val teamLeaders: List<TeamLeader> = emptyList(),
    val threeMinRecap: String? = null,
    val threeMinRecapFr: String? = null,
    val condensedGame: String? = null,
    val condensedGameFr: String? = null,
    val goals: List<Goal> = emptyList()
)

data class Venue(
    val default: String
)

data class TvBroadcast(
    val id: Int,
    val market: String,
    val countryCode: String,
    val network: String,
    val sequenceNumber: Int
)

data class Team(
    val id: Int,
    val name: TeamNameGames,
    val abbrev: String,
    val score: Int? = null,
    val sog: Int? = null,
    val logo: String,
    val odds: List<TeamOdds> = emptyList()
)

data class TeamNameGames(
    val default: String,
    val fr: String? = null
)

data class TeamOdds(
    val providerId: Int,
    val value: String
)

data class TeamLeader(
    val id: Int,
    val firstName: TranslatedName,
    val lastName: TranslatedName,
    val headshot: String,
    val teamAbbrev: String,
    val category: String,
    val value: Int
)

data class TranslatedName(
    val default: String,
    val cs: String? = null,
    val fi: String? = null,
    val sk: String? = null
)

data class Goal(
    val period: Int,
    val periodDescriptor: PeriodDescriptor,
    val timeInPeriod: String,
    val playerId: Int,
    val name: TranslatedName,
    val firstName: TranslatedName,
    val lastName: TranslatedName,
    val goalModifier: String? = null,
    val assists: List<Assist> = emptyList(),
    val mugshot: String,
    val teamAbbrev: String,
    val goalsToDate: Int,
    val awayScore: Int,
    val homeScore: Int,
    val strength: String,
    val highlightClipSharingUrl: String,
    val highlightClipSharingUrlFr: String? = null
)

data class PeriodDescriptor(
    val number: Int,
    val periodType: String,
    val maxRegulationPeriods: Int
)

data class Assist(
    val playerId: Int,
    val name: TranslatedName,
    val assistsToDate: Int
)
