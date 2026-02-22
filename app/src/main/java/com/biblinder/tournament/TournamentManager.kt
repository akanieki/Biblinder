package com.biblinder.tournament

import kotlin.random.Random

data class Match(val anime1: String, val anime2: String)
data class Bracket(val matches: List<Match>)

class TournamentManager {

    fun buildBracket(animeList: List<String>): Bracket {
        require(animeList.size % 2 == 0) { "Anime count must be even!" }
        val shuffled = animeList.shuffled(Random(System.nanoTime()))
        val matches = shuffled.chunked(2).map { Match(it[0], it[1]) }
        return Bracket(matches)
    }

    fun nextRound(winners: List<String>): Bracket {
        return buildBracket(winners)
    }
}
