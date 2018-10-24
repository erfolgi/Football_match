package erfolgi.com.football_match.models

import com.google.gson.annotations.SerializedName



data class Team (
        @SerializedName("strTeamBadge")
        var strTeamBadge:String?=null
)