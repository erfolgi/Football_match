package erfolgi.com.football_match.db.table

data class Favorites(val id: Long?,
                     val eventId: String?,
                     val eventDate: String?,
                     val eventTime: String?,
                     val homeTeam: String?,
                     val homeScore: String?,
                     val awayTeam: String?,
                     val awayScore: String?) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val DATE: String = "DATE"
        const val TIME: String = "TIME"
        const val EVENT_ID: String = "EVENT_ID"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val AWAY_SCORE: String = "AWAY_SCORE"
    }
}