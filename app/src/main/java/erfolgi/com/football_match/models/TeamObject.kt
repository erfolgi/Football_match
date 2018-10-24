package erfolgi.com.football_match.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class TeamObject {
    @SerializedName("teams")
    @Expose
    val teams: List<Team>? = null
}