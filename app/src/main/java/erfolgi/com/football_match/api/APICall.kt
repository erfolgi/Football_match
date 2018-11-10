package erfolgi.com.football_match.api


import erfolgi.com.football_match.models.JSONobject
import erfolgi.com.football_match.models.PlayerObject
import erfolgi.com.football_match.models.TeamObject
import retrofit2.Call
import retrofit2.http.GET

import retrofit2.http.Query


interface APICall {
    @GET("eventspastleague.php?id=4328")
    fun requestDataLast(): Call<JSONobject>

    @GET("eventspastleague.php?id=4328")
    fun requestLastByLeague(@Query("id") idLeague: String): Call<JSONobject>

    @GET("lookupevent.php")//?id=576555
    fun requestDetailLast(@Query("id") idEvent: String): Call<JSONobject>

    @GET("eventsnextleague.php?id=4328")
    fun requestDataNext(): Call<JSONobject>

    @GET("eventsnextleague.php")
    fun requestNextByLeague(@Query("id") idLeague: String): Call<JSONobject>

    @GET("lookupteam.php")
    fun requestTeam(@Query("id") id: String): Call<TeamObject>

    @GET("search_all_teams.php")
    fun requestTeamByLeague(@Query("l") l: String): Call<TeamObject>

    @GET("lookup_all_players.php")
    fun requestAllPlayersByTeam(@Query("id") id: String): Call<PlayerObject>

    @GET("lookupplayer.php")//lookupplayer.php?id=34145937
    fun requestDetailPlayerById(@Query("id") id: String): Call<PlayerObject>

    @GET("searchteams.php")//searchteams.php?t=Arsenal
    fun requestSearchTeam(@Query("t") t: String): Call<TeamObject>

    @GET("searchevents.php")//searchevents.php?e=chel&s=1617
    fun requestSearchMatch(@Query("e") t: String,@Query("e" ) s: String ="1617"): Call<TeamObject>



}