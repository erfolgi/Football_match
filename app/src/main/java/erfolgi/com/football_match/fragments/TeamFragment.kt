package erfolgi.com.football_match.fragments


import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import erfolgi.com.football_match.R
import erfolgi.com.football_match.adapters.TeamAdapter
import erfolgi.com.football_match.api.APIClient
import erfolgi.com.football_match.models.Team
import erfolgi.com.football_match.models.TeamObject
import kotlinx.android.synthetic.main.fragment_team.*

import org.jetbrains.anko.support.v4.ctx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TeamFragment : Fragment() {
    private val apiClient = APIClient()
    lateinit var apiCall: Call<TeamObject>
    lateinit var RV: RecyclerView
    lateinit var tmdapter: TeamAdapter
    private var items: MutableList<Team> = mutableListOf()
    var objek: TeamObject?=null
    lateinit var swipeRefresh: SwipeRefreshLayout
//    lateinit var con: Context
    lateinit var spinner: Spinner
    var id_league="English Premier League"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_team, container, false)
        swipeRefresh=view.findViewById(R.id.SR_Team)
        RV = view.findViewById(R.id.rv_team) as RecyclerView
        RV.addItemDecoration(DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL))
        RV.layoutManager = LinearLayoutManager(ctx)
        //con= context as Context
        spinner=view.findViewById(R.id.spin_team)
        //val spinnerID = resources.getStringArray(R.array.leagueID)

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter
        ///////////////////////////////////////////////////////////
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                Log.d("array spin","isi"+spinnerItems[position])
                id_league= spinnerItems[position]
                swipeRefresh.post {
                    swipeRefresh.isRefreshing = true
                    LoadAPI(id_league)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        swipeRefresh.post {
            swipeRefresh.isRefreshing = true
            Log.e("gedebug","Refresh")
            Log.e("gedebug","onRefresh")
            LoadAPI(id_league)
        }

        swipeRefresh.setOnRefreshListener {
            Log.e("gedebug","Swipe")
            LoadAPI(id_league)
            items.clear()
        }
        return view
    }

    private fun LoadAPI(id:String){
        Log.e("gedebug","Load")

        apiCall = this.apiClient.service.requestTeamByLeague(id)
        apiCall.enqueue(object : Callback<TeamObject> {

            override fun onResponse(call : Call<TeamObject>, response : Response<TeamObject>){
                Log.d("gedebug",response.body().toString())

                if(response.isSuccessful){
                    objek= response.body()
                    teamfailed?.visibility=(View.INVISIBLE)
                    items= objek?.teams as MutableList<Team>
                    tmdapter = TeamAdapter(ctx, items)
                    RV.adapter = tmdapter
                    swipeRefresh.isRefreshing = false
                }
            }

            @SuppressLint("SetTextI18n")
            override fun  onFailure(call :Call<TeamObject>, t: Throwable) {
                //Toast.makeText(context, "Data Tidak bisa diambil"+t, Toast.LENGTH_LONG).show();
                Log.e("gedebug",t.toString())
                swipeRefresh.isRefreshing = false

                teamfailed.visibility=(View.VISIBLE)//setvisibility(View.INVISIBLE);
                teamfailed.text="Connection Failed."
            }
        })

    }

}
