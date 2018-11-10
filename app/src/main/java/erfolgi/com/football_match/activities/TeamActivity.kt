package erfolgi.com.football_match.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
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
import erfolgi.com.football_match.models.Team
import erfolgi.com.football_match.models.TeamObject
import kotlinx.android.synthetic.main.activity_team.*
import org.jetbrains.anko.ctx
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
        Log.e("ppppp",EXTRA_NAME)
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
        Log.e("ssssss", EXTRA_ID_TEAM)
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

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        //setFavorite()
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_to_favorite -> {
//                if(isFavorite){
//                    removeFromFavorite()
//                }else{
//                    addToFavorite()
//                }
//                favoriteState()
//                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}
