package erfolgi.com.football_match.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import erfolgi.com.football_match.R

import erfolgi.com.football_match.api.APIClient
import erfolgi.com.football_match.models.Event
import erfolgi.com.football_match.models.JSONobject
import erfolgi.com.football_match.models.Team
import erfolgi.com.football_match.models.TeamObject
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private val apiClient = APIClient()
    lateinit var apiCall: Call<JSONobject>
    private var items: MutableList<Event> = mutableListOf()
    private var logos: MutableList<Team> = mutableListOf()
    lateinit var objek:JSONobject
    lateinit var tim:TeamObject

    lateinit var logoCall: Call<TeamObject>
    lateinit var logo2Call: Call<TeamObject>

    companion object {
        var EXTRA_ID = "id event"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar!!.title = "Match Detail"
        EXTRA_ID= intent.getStringExtra(EXTRA_ID)

        apiCall = this.apiClient.service.requestDetailLast(EXTRA_ID)
        apiCall.enqueue(object : Callback<JSONobject> {

            override fun onResponse(call : Call<JSONobject>, response : Response<JSONobject>){
                Log.d("gedebug",response.body().toString())
                if(response.isSuccessful){
                    objek= response.body()!!
                    items= objek.events as MutableList<Event>
                    match_date.text=items[0].eventDate

                    home_team.text=items[0].homeTeam
                    home_score.text=items[0].homeScore

                    away_team.text=items[0].awayTeam
                    away_score.text=items[0].awayScore

                    if(items[0].homeGoalkeeper!=null && items[0].awayGoalkeeper!=null){
                        home_gk.text= items[0].homeGoalkeeper!!.replace("; ","\n")
                        home_df.text=items[0].homeDefense!!.replace("; ","\n")
                        home_mf.text=items[0].homeMidfield!!.replace("; ","\n")
                        home_fw.text=items[0].homeForward!!.replace("; ","\n")
                        home_sub.text=items[0].homeSubstitutes!!.replace("; ","\n")

                        away_gk.text= items[0].awayGoalkeeper!!.replace("; ","\n")
                        away_df.text=items[0].awayDefense!!.replace("; ","\n")
                        away_mf.text=items[0].awayMidfield!!.replace("; ","\n")
                        away_fw.text=items[0].awayForward!!.replace("; ","\n")
                        away_sub.text=items[0].awaySubstitutes!!.replace("; ","\n")
                    }


                    home_shot.text=items[0].homeShots
                    away_shot.text=items[0].awayShots

                    if(items[0].homeGoalDetails!=null && items[0].awayGoalDetails!=null){
                        home_goal.text=items[0].homeGoalDetails!!.replace(";","\n")
                        away_goal.text=items[0].awayGoalDetails!!.replace(";","\n")
                    }

                    val logoClient = APIClient()
                    logoCall=logoClient.service.requestTeam(items[0].homeTeamId!!)
                    logoCall.enqueue(object : Callback<TeamObject> {
                        override fun onFailure(call: Call<TeamObject>, t: Throwable) {

                        }
                        override fun onResponse(call: Call<TeamObject>, response: Response<TeamObject>) {
                            tim= response.body()!!
                            logos= tim.teams as MutableList<Team>
                            Glide.with(this@DetailActivity).load(logos[0].strTeamBadge).into(home_image)
                        }
                    })

                    logo2Call=logoClient.service.requestTeam(items[0].awayTeamId!!)
                    logo2Call.enqueue(object : Callback<TeamObject> {
                        override fun onFailure(call: Call<TeamObject>, t: Throwable) {

                        }
                        override fun onResponse(call: Call<TeamObject>, response: Response<TeamObject>) {
                            tim= response.body()!!
                            logos= tim.teams as MutableList<Team>
                            Glide.with(this@DetailActivity).load(logos[0].strTeamBadge).into(away_image)
                        }
                    })

                }
            }

            override fun  onFailure(call : Call<JSONobject>, t: Throwable) {
                Toast.makeText(this@DetailActivity, "Data Tidak bisa diambil"+t, Toast.LENGTH_LONG).show();
                Log.e("gedebugcoy",t.toString())
            }
        })

    }
}
