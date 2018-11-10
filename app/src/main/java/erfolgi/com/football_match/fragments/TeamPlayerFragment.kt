package erfolgi.com.football_match.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG

import erfolgi.com.football_match.R
import erfolgi.com.football_match.activities.TeamActivity.Companion.EXTRA_ID_TEAM

import erfolgi.com.football_match.adapters.PlayerListAdapter
import erfolgi.com.football_match.api.APIClient
import erfolgi.com.football_match.models.Player
import erfolgi.com.football_match.models.PlayerObject
import org.jetbrains.anko.support.v4.ctx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TeamPlayerFragment : Fragment() {
    private val apiClient = APIClient()
    lateinit var apiCall: Call<PlayerObject>
    lateinit var RV: RecyclerView
    lateinit var tmdapter: PlayerListAdapter
    var items: MutableList<Player>? =null
    var objek: PlayerObject?=null

    lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_team_player, container, false)
        swipeRefresh=view.findViewById(R.id.SR_Player)
        RV = view.findViewById(R.id.rv_player) as RecyclerView
        RV.addItemDecoration(DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL))
        RV.layoutManager = LinearLayoutManager(ctx)

        swipeRefresh.post {
            swipeRefresh.isRefreshing = true
            Log.e("gedebug","Refresh")
            Log.e("gedebug","onRefresh")
            LoadAPI(EXTRA_ID_TEAM)
        }

        swipeRefresh.setOnRefreshListener {
            Log.e("gedebug","Swipe")
            LoadAPI(EXTRA_ID_TEAM)
            items?.clear()
        }

        return view
    }
    private fun LoadAPI(id:String){

        apiCall = this.apiClient.service.requestAllPlayersByTeam(id)
        apiCall.enqueue(object : Callback<PlayerObject> {

            override fun onResponse(call : Call<PlayerObject>, response : Response<PlayerObject>){

                if(response.isSuccessful &&response.body()!=null){
                    objek= response.body()
                    Log.e("objek", response.toString())
                    Log.e("objek", response.body().toString())
                    Log.e("objek", response.errorBody().toString())
                   items= objek?.players as MutableList<Player>
                    Log.e("item", items!![0].strPlayer)
                    tmdapter = PlayerListAdapter(ctx, items!!)
                    RV.adapter = tmdapter
                    swipeRefresh.isRefreshing = false
                }
            }

            @SuppressLint("SetTextI18n")
            override fun  onFailure(call :Call<PlayerObject>, t: Throwable) {
                Toast.makeText(ctx, "Data Tidak bisa diambil"+t, LENGTH_LONG).show();
                swipeRefresh.isRefreshing = false

            }
        })

    }
}
