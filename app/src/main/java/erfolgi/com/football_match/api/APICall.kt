package erfolgi.com.football_match.api


import erfolgi.com.football_match.models.JSONobject
import erfolgi.com.football_match.models.TeamObject
import retrofit2.Call
import retrofit2.http.GET

import retrofit2.http.Query


interface APICall {
    @GET("eventspastleague.php?id=4328")
    fun requestDataLast(): Call<JSONobject>

    @GET("lookupevent.php")//?id=576555
    fun requestDetailLast(@Query("id") idEvent: String): Call<JSONobject>

    @GET("eventsnextleague.php?id=4328")
    fun requestDataNext(): Call<JSONobject>

    @GET("lookupteam.php")
    fun requestTeam(@Query("id") id: String): Call<TeamObject>


}