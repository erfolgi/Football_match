package erfolgi.com.football_match.fragments


import android.annotation.SuppressLint
import android.content.Context
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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import erfolgi.com.football_match.R
import erfolgi.com.football_match.adapters.NextMatchAdapter
import erfolgi.com.football_match.api.APIClient
import erfolgi.com.football_match.models.Event
import erfolgi.com.football_match.models.JSONobject
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.support.v4.ctx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 *
 */
open class NextMatchFragment : Fragment() {
    private val apiClient = APIClient()
    lateinit var apiCall: Call<JSONobject>
    lateinit var RV: RecyclerView
    lateinit var lmadapter: NextMatchAdapter
    private var items: MutableList<Event> = mutableListOf()
    var objek: JSONobject?=null
    lateinit var swipeRefresh: SwipeRefreshLayout
    lateinit var con: Context
    lateinit var spinner: Spinner
    var id_league="4328"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_next_match, container, false)
        swipeRefresh=view.findViewById(R.id.SR_Next)
        RV = view.findViewById(R.id.rv_nextmatch) as RecyclerView
        RV.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        RV.layoutManager = LinearLayoutManager(context)
        con= context as Context
        val spinnerID = resources.getStringArray(R.array.leagueID)
        spinner=view.findViewById(R.id.spin_next)
        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                id_league= spinnerID[position].replace("f","")
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
            LoadAPI(id_league)

        }

        swipeRefresh.setOnRefreshListener {
            Log.e("gedebug","Swipe")
            LoadAPI(id_league)
        }



        return view
    }
    fun LoadAPI(id:String){
        Log.e("gedebug","Loading")
        apiCall = this.apiClient.service.requestNextByLeague(id)
        apiCall.enqueue(object : Callback<JSONobject> {

            override fun onResponse(call : Call<JSONobject>, response : Response<JSONobject>){
                Log.e("gedebug","Response")
                if(response.isSuccessful){
                    nextfailed?.visibility=(View.INVISIBLE)
                    objek= response.body()
                    items= objek?.events as MutableList<Event>
                    lmadapter = NextMatchAdapter(con, items){

                    }
                    RV.adapter = lmadapter
                    swipeRefresh.isRefreshing = false
                }
            }

            @SuppressLint("SetTextI18n")
            override fun  onFailure(call :Call<JSONobject>, t: Throwable) {
                //Toast.makeText(context, "Data Tidak bisa diambil"+t, Toast.LENGTH_LONG).show();
                swipeRefresh.isRefreshing = false
                nextfailed.visibility=(View.VISIBLE)//setvisibility(View.INVISIBLE);
                nextfailed.text="Connection Failed."
                Log.e("gedebug",t.toString())
                Log.e("gedebug","Failed")
            }
        })
    }


}
