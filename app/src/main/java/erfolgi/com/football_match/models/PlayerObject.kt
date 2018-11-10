package erfolgi.com.football_match.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PlayerObject {

    @SerializedName("player")
    @Expose
    var players: List<Player>? = null

    @SerializedName("players")
    @Expose
    var playerss: List<Player>? = null

}