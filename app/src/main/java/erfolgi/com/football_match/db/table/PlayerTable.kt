package erfolgi.com.football_match.db.table

class PlayerTable (val id: Long?,
                   val idPlayer: String?,
                   val strCutout: String?,
                   val strPlayer: String?,
                   val strPosition: String?) {

    companion object {
        const val TABLE_PLAYER: String = "TABLE_PLAYER"
        const val ID: String = "ID_"
        const val PLAYER_ID: String = "PLAYER_ID"
        const val PLAYER_IMAGE: String = "PLAYER_IMAGE"
        const val PLAYER_NAME: String = "PLAYER_NAME"
        const val PLAYER_POSITION: String = "PLAYER_POSITION"


    }
}