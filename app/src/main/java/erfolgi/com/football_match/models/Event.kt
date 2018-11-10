package erfolgi.com.football_match.models


import com.google.gson.annotations.SerializedName

data class Event (
        @SerializedName("idEvent")
        var eventId: String? = null,

        @SerializedName("strEvent")
        var strEvent: String? = null,

        @SerializedName("dateEvent")
        var eventDate: String? = null,

        @SerializedName("strTime")
        var eventTime: String?=null,

        @SerializedName("strLeague")
        var eventLeague: String?=null,

        // Home
        @SerializedName("strHomeTeam")
        var homeTeam: String? = null,

        @SerializedName("idHomeTeam")
        var homeTeamId: String? = null,

        @SerializedName("intHomeScore")
        var homeScore: String? = null,

        // Away
        @SerializedName("strAwayTeam")
        var awayTeam: String? = null,

        @SerializedName("idAwayTeam")
        var awayTeamId: String? = null,

        @SerializedName("intAwayScore")
        var awayScore: String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        var awayGoalkeeper: String? = null,

        @SerializedName("strAwayLineupDefense")
        var awayDefense: String? = null,

        @SerializedName("strAwayLineupMidfield")
        var awayMidfield: String? = null,

        @SerializedName("strAwayLineupForward")
        var awayForward: String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        var awaySubstitutes: String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        var homeGoalkeeper: String? = null,

        @SerializedName("strHomeLineupDefense")
        var homeDefense: String? = null,

        @SerializedName("strHomeLineupMidfield")
        var homeMidfield: String? = null,

        @SerializedName("strHomeLineupForward")
        var homeForward: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        var homeSubstitutes: String? = null,

        @SerializedName("intHomeShots")
        var homeShots: String? = null,

        @SerializedName("intAwayShots")
        var awayShots: String? = null,

        @SerializedName("strHomeGoalDetails")
        var homeGoalDetails: String?=null,

        @SerializedName("strAwayGoalDetails")
        var awayGoalDetails: String?=null
)