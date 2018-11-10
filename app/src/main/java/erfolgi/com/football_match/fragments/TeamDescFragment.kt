package erfolgi.com.football_match.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import erfolgi.com.football_match.R
import erfolgi.com.football_match.activities.TeamActivity
import erfolgi.com.football_match.activities.TeamActivity.Companion.EXTRA_ID_TEAM
import erfolgi.com.football_match.api.APIClient
import erfolgi.com.football_match.models.Team
import erfolgi.com.football_match.models.TeamObject
import kotlinx.android.synthetic.main.fragment_team_desc.*
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
class TeamDescFragment : Fragment() {
    private val apiClient = APIClient()
    lateinit var apiCall: Call<TeamObject>
    private var items: MutableList<Team> = mutableListOf()
    var objek: TeamObject?=null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_team_desc, container, false)

        apiCall = this.apiClient.service.requestTeam(EXTRA_ID_TEAM)
        apiCall.enqueue(object : Callback<TeamObject> {

            override fun onResponse(call : Call<TeamObject>, response : Response<TeamObject>){
                if(response.isSuccessful){
                    objek= response.body()
                    items= objek?.teams as MutableList<Team>

                    tv_team_desc.text=items[0].strDescriptionEN
                }
            }

            override fun  onFailure(call :Call<TeamObject>, t: Throwable) {
                Log.e("gedebug",t.toString())
                Toast.makeText(ctx,"Failed: "+t, Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }


}
