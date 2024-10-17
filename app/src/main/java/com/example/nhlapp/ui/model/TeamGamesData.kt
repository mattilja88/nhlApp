package com.example.nhlapp.ui.model

data class Schedule(
    val previousStartDate: String,
    val nextStartDate: String,
    val calendarUrl: String,
    val clubTimezone: String,
    val clubUTCOffset: String,
    val games: List<TeamGame>
)

data class TeamGame(
    val id: Long,
    val season: Int,
    val gameType: Int,
    val gameDate: String,
    val venue: TeamVenue,
    val neutralSite: Boolean,
    val startTimeUTC: String,
    val easternUTCOffset: String,
    val venueUTCOffset: String,
    val venueTimezone: String,
    val gameState: String,
    val gameScheduleState: String,
    val tvBroadcasts: List<TeamTvBroadcast>,
    val awayTeam: TeamDetails,
    val homeTeam: TeamDetails,
    val periodDescriptor: TeamPeriodDescriptor,
    val gameOutcome: GameOutcome?,
    val winningGoalie: Player?,
    val winningGoalScorer: Player?,
    val threeMinRecap: String?,
    val threeMinRecapFr: String?,
    val gameCenterLink: String,
    val ticketsLink: String? = null,
    val ticketsLinkFr: String? = null
)

data class TeamVenue(
    val default: String,
    val es: String? = null,
    val fr: String? = null
)

data class TeamTvBroadcast(
    val id: Int,
    val market: String,
    val countryCode: String,
    val network: String,
    val sequenceNumber: Int
)

data class TeamDetails(
    val id: Int,
    val placeName: TeamPlaceName,
    val placeNameWithPreposition: PlaceNameWithPreposition,
    val abbrev: String,
    val logo: String,
    val darkLogo: String,
    val awaySplitSquad: Boolean = false,
    val homeSplitSquad: Boolean = false,
    val airlineLink: String? = null,
    val airlineDesc: String? = null,
    val hotelLink: String? = null,
    val hotelDesc: String? = null,
    val score: Int? = null,
    val radioLink: String? = null
)

data class TeamPlaceName(
    val default: String
)

data class PlaceNameWithPreposition(
    val default: String,
    val fr: String? = null
)

data class TeamPeriodDescriptor(
    val number: Int,
    val periodType: String,
    val maxRegulationPeriods: Int
)

data class GameOutcome(
    val lastPeriodType: String
)

data class Player(
    val playerId: Long,
    val firstInitial: Initial,
    val lastName: LastName
)

data class Initial(
    val default: String
)

data class LastName(
    val default: String
)


