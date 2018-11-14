package erfolgi.com.football_match.db.table

class TeamTable (val id: Long?,
                 val idTeam: String?,
                 val strTeamBadge: String?,
                 val strTeam: String?) {

    companion object {
        const val TABLE_TEAM: String = "TABLE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_BADGE: String = "TEAM_BADGE"
        const val TEAM_NAME: String = "TEAM_NAME"

    }
}