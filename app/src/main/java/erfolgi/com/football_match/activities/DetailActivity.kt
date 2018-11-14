package erfolgi.com.football_match.activities

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import erfolgi.com.football_match.R
import erfolgi.com.football_match.R.drawable.ic_fav_no
import erfolgi.com.football_match.R.drawable.ic_fav_yes
import erfolgi.com.football_match.api.APIClient
import erfolgi.com.football_match.db.database
import erfolgi.com.football_match.db.table.Favorites
import erfolgi.com.football_match.models.*
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {
    private val apiClient = APIClient()
    lateinit var apiCall: Call<JSONobject>
    private var items: MutableList<Event> = mutableListOf()
    private var logos: MutableList<Team> = mutableListOf()
    lateinit var objek:JSONobject
    var tim:TeamObject?=null

    lateinit var logoCall: Call<TeamObject>
    lateinit var logo2Call: Call<TeamObject>

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    companion object {
        var EXTRA_ID = "id event"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        EXTRA_ID= intent.getStringExtra(EXTRA_ID)
        favoriteState()
        apiCall = this.apiClient.service.requestDetailLast(EXTRA_ID)
        apiCall.enqueue(object : Callback<JSONobject> {

            override fun onResponse(call : Call<JSONobject>, response : Response<JSONobject>){
                Log.d("gedebug",response.body().toString())
                if(response.isSuccessful){
                    objek= response.body()!!
                    items= objek.events as MutableList<Event>
                    val timeconvert = toGMTFormat(items[0].eventDate,items[0].eventTime)
                    val formatDate = SimpleDateFormat("E, dd MM yyyy")
                    val formatTime = SimpleDateFormat("HH:mm")
                    val date = formatDate.format(timeconvert)
                    val time = formatTime.format(timeconvert)
                    match_date.text = date
                    match_time.text = time
                    match_league.text=items[0].eventLeague

                    home_team.text=items[0].homeTeam
                    home_score.text=items[0].homeScore

                    away_team.text=items[0].awayTeam
                    away_score.text=items[0].awayScore

                    if(items[0].homeGoalkeeper!=null && items[0].awayGoalkeeper!=null){
                        home_gk.text= items[0].homeGoalkeeper?.replace("; ","\n")
                        home_df.text=items[0].homeDefense?.replace("; ","\n")
                        home_mf.text=items[0].homeMidfield?.replace("; ","\n")
                        home_fw.text=items[0].homeForward?.replace("; ","\n")
                        home_sub.text=items[0].homeSubstitutes?.replace("; ","\n")

                        away_gk.text= items[0].awayGoalkeeper?.replace("; ","\n")
                        away_df.text=items[0].awayDefense?.replace("; ","\n")
                        away_mf.text=items[0].awayMidfield?.replace("; ","\n")
                        away_fw.text=items[0].awayForward?.replace("; ","\n")
                        away_sub.text=items[0].awaySubstitutes?.replace("; ","\n")
                    }


                    home_shot.text=items[0].homeShots
                    away_shot.text=items[0].awayShots

                    if(items[0].homeGoalDetails!=null && items[0].awayGoalDetails!=null){
                        home_goal.text=items[0].homeGoalDetails?.replace(";","\n")
                        away_goal.text=items[0].awayGoalDetails?.replace(";","\n")
                    }

                    val logoClient = APIClient()
                    logoCall=logoClient.service.requestTeam(items[0].homeTeamId!!)
                    logoCall.enqueue(object : Callback<TeamObject> {
                        override fun onFailure(call: Call<TeamObject>, t: Throwable) {

                        }
                        override fun onResponse(call: Call<TeamObject>, response: Response<TeamObject>) {
                            tim= response.body()
                            logos= tim?.teams as MutableList<Team>
                            Glide.with(this@DetailActivity).load(logos[0].strTeamBadge).into(home_image)
                        }
                    })

                    logo2Call=logoClient.service.requestTeam(items[0].awayTeamId!!)
                    logo2Call.enqueue(object : Callback<TeamObject> {
                        override fun onFailure(call: Call<TeamObject>, t: Throwable) {

                        }
                        override fun onResponse(call: Call<TeamObject>, response: Response<TeamObject>) {
                            tim= response.body()
                            logos= tim?.teams as MutableList<Team>
                            Glide.with(this@DetailActivity).load(logos[0].strTeamBadge).into(away_image)
                        }
                    })

                }
            }
            private fun toGMTFormat(date: String?, time: String?): Date? {
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                formatter.timeZone = TimeZone.getTimeZone("UTC")
                val dateTime = "$date $time"
                return formatter.parse(dateTime)
            }

            override fun  onFailure(call : Call<JSONobject>, t: Throwable) {
                Toast.makeText(this@DetailActivity, "Data Tidak bisa diambil"+t, Toast.LENGTH_LONG).show();
                Log.e("gedebugcoy",t.toString())
            }
        })

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if(isFavorite){
                    removeFromFavorite()
                }else{
                    addToFavorite()
                }
                favoriteState()
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(Favorites.TABLE_FAVORITE,
                        Favorites.DATE to items[0].eventDate,
                        Favorites.TIME to items[0].eventTime,
                        Favorites.EVENT_ID to items[0].eventId,
                        Favorites.HOME_TEAM to items[0].homeTeam,
                        Favorites.HOME_SCORE to items[0].homeScore,
                        Favorites.AWAY_TEAM to items[0].awayTeam,
                        Favorites.AWAY_SCORE to items[0].awayScore)
            }

            Toast.makeText(this, "Added to favorite", Toast.LENGTH_LONG).show()
        } catch (e: SQLiteConstraintException){
            Toast.makeText(this, "Error: $e", Toast.LENGTH_LONG).show()

        }
    }
    private fun favoriteState(){
        database.use {
           // createTable(TABLE_FAVORITE)
            val result = select(Favorites.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {EXTRA_ID})",
                            "EXTRA_ID" to EXTRA_ID)
            val favor = result.parseList(classParser<Favorites>())
            isFavorite = favor.isEmpty() != true
            //isFavorite = !favor.isEmpty()
        }
    }
    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_fav_yes)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_fav_no)
    }
    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorites.TABLE_FAVORITE, "(EVENT_ID = {EXTRA_ID})",
                        "EXTRA_ID" to EXTRA_ID)
            }

            Toast.makeText(this, "Removed from favorite", Toast.LENGTH_LONG).show();

        } catch (e: SQLiteConstraintException){
            Toast.makeText(this, "Error Remove :$e", Toast.LENGTH_LONG).show();

        }
    }
}
