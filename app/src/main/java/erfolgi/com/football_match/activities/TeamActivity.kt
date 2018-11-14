package erfolgi.com.football_match.activities

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.bumptech.glide.Glide
import erfolgi.com.football_match.R
import erfolgi.com.football_match.adapters.TeamTabbedDetailAdapter
import erfolgi.com.football_match.api.APIClient
import erfolgi.com.football_match.db.database
import erfolgi.com.football_match.db.table.TeamTable
import erfolgi.com.football_match.models.Team
import erfolgi.com.football_match.models.TeamObject
import kotlinx.android.synthetic.main.activity_team.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    private val apiClient = APIClient()
    lateinit var apiCall: Call<TeamObject>
    private var items: MutableList<Team> = mutableListOf()
    var objek: TeamObject?=null
    private var isFavorite: Boolean = false

    private var menuItem: Menu? = null
    companion object {
        var EXTRA_ID_TEAM = "id team"
        var EXTRA_NAME = "team name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
        setSupportActionBar(toolbar_team)
        EXTRA_NAME= intent.getStringExtra(EXTRA_NAME)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = EXTRA_NAME

        //Log.e("rrrrrr", supportActionBar?.title as String)
        EXTRA_ID_TEAM= intent.getStringExtra(EXTRA_ID_TEAM)
        //////////////////////////////////////////////////////////////////////////
        tabLayout = findViewById(R.id.tabs_team)
        viewPager = findViewById(R.id.view_pager_team)
        viewPager.adapter = TeamTabbedDetailAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

/////////////////////////////////////////////////////////////////////////////////////////////////////////
        apiCall = this.apiClient.service.requestTeam(EXTRA_ID_TEAM)
        apiCall.enqueue(object : Callback<TeamObject> {

            override fun onResponse(call : Call<TeamObject>, response : Response<TeamObject>){
                if(response.isSuccessful){
                    objek= response.body()
                    items= objek?.teams as MutableList<Team>
                    Glide.with(ctx).load(items[0].strTeamBadge).into(iv_team_image)
                    tv_team_stadium.text=items[0].strStadium
                    tv_team_year.text=items[0].intFormedYear

                }
            }

            override fun  onFailure(call :Call<TeamObject>, t: Throwable) {
                Log.e("gedebug",t.toString())
                Toast.makeText(ctx,"Failed: "+t,LENGTH_SHORT).show()
            }
        })
        favoriteState()
        setFavorite()

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
                insert(TeamTable.TABLE_TEAM,
                        TeamTable.TEAM_ID to items[0].idTeam,
                        TeamTable.TEAM_BADGE to items[0].strTeamBadge,
                        TeamTable.TEAM_NAME to items[0].strTeam)
            }

            Toast.makeText(this, "Added to favorite", Toast.LENGTH_LONG).show()
        } catch (e: SQLiteConstraintException){
            Toast.makeText(this, "Error: $e", Toast.LENGTH_LONG).show()

        }
    }
    private fun favoriteState(){
        database.use {
            // createTable(TABLE_FAVORITE)
            val result = select(TeamTable.TABLE_TEAM)
                    .whereArgs("(TEAM_ID = {EXTRA_ID_TEAM})",
                            "EXTRA_ID_TEAM" to EXTRA_ID_TEAM)
            val favor = result.parseList(classParser<TeamTable>())
            isFavorite = favor.isEmpty() != true
            //isFavorite = !favor.isEmpty()
        }
    }
    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_fav_yes)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_fav_no)
    }
    private fun removeFromFavorite(){
        try {
            database.use {
                delete(TeamTable.TABLE_TEAM, "(TEAM_ID = {EXTRA_ID_TEAM})",
                        "EXTRA_ID_TEAM" to EXTRA_ID_TEAM)
            }

            Toast.makeText(this, "Removed from favorite", Toast.LENGTH_LONG).show();

        } catch (e: SQLiteConstraintException){
            Toast.makeText(this, "Error Remove :$e", Toast.LENGTH_LONG).show();

        }
    }

}
