package erfolgi.com.football_match.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import erfolgi.com.football_match.R
import erfolgi.com.football_match.api.APIClient
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import erfolgi.com.football_match.models.Event
import retrofit2.Call
import retrofit2.Callback

import retrofit2.Response
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import erfolgi.com.football_match.adapters.LastMatchAdapter
import erfolgi.com.football_match.models.JSONobject



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LastMatchFragment : Fragment() {
    private val apiClient = APIClient()
    lateinit var apiCall: Call<JSONobject>
    lateinit var RV: RecyclerView
    lateinit var lmadapter: LastMatchAdapter
    private var items: MutableList<Event> = mutableListOf()
   // lateinit var call: Call<JSONobject>
    lateinit var objek:JSONobject
    lateinit var swipeRefresh:SwipeRefreshLayout



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_last_match, container, false)
        swipeRefresh=view.findViewById(R.id.SR_Last)

        apiCall = this.apiClient.service.requestDataLast()
        apiCall.enqueue(object : Callback<JSONobject> {

            override fun onResponse(call : Call<JSONobject>, response : Response<JSONobject> ){
                Log.d("gedebug",response.body().toString())
                if(response.isSuccessful){
                    objek= response.body()!!
                    items= objek.events as MutableList<Event>

                RV = view.findViewById(R.id.rv_lastmatch) as RecyclerView
                RV.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))
                RV.layoutManager = LinearLayoutManager(context)

                lmadapter = LastMatchAdapter(context!!, items){

                }
                RV.adapter = lmadapter
                }
            }

            override fun  onFailure(call :Call<JSONobject>, t: Throwable) {
                Toast.makeText(context, "Data Tidak bisa diambil"+t, Toast.LENGTH_LONG).show();
                Log.e("gedebug",t.toString())
            }
        })

        swipeRefresh.setOnRefreshListener {
            apiCall = this.apiClient.service.requestDataLast()
            apiCall.enqueue(object : Callback<JSONobject> {

                override fun onResponse(call : Call<JSONobject>, response : Response<JSONobject> ){
                    Log.d("gedebug",response.body().toString())
                    if(response.isSuccessful){
                        objek= response.body()!!
                        items= objek.events as MutableList<Event>

                        RV = view.findViewById(R.id.rv_lastmatch) as RecyclerView
                        RV.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))
                        RV.layoutManager = LinearLayoutManager(context)

                        lmadapter = LastMatchAdapter(context!!, items){

                        }
                        RV.adapter = lmadapter
                    }
                }

                override fun  onFailure(call :Call<JSONobject>, t: Throwable) {
                    Toast.makeText(context, "Data Tidak bisa diambil"+t, Toast.LENGTH_LONG).show();
                    Log.e("gedebug",t.toString())
                }
            })


            swipeRefresh.isRefreshing = false
        }

        return view
    }


}