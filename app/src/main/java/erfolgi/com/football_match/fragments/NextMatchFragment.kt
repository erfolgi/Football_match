package erfolgi.com.football_match.fragments


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

import erfolgi.com.football_match.R
import erfolgi.com.football_match.adapters.LastMatchAdapter
import erfolgi.com.football_match.api.APIClient
import erfolgi.com.football_match.models.Event
import erfolgi.com.football_match.models.JSONobject
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
open class NextMatchFragment : Fragment() {
    private val apiClient = APIClient()
    lateinit var apiCall: Call<JSONobject>
    lateinit var RV: RecyclerView
    lateinit var lmadapter: LastMatchAdapter
    private var items: MutableList<Event> = mutableListOf()
    // lateinit var call: Call<JSONobject>
    lateinit var objek: JSONobject
    lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_next_match, container, false)
        swipeRefresh=view.findViewById(R.id.SR_Next)

        apiCall = this.apiClient.service.requestDataNext()
        apiCall.enqueue(object : Callback<JSONobject> {

            override fun onResponse(call : Call<JSONobject>, response : Response<JSONobject>){
                Log.d("gedebug",response.body().toString())
                if(response.isSuccessful){
                    objek= response.body()!!
                    items= objek.events as MutableList<Event>

                    RV = view.findViewById(R.id.rv_nextmatch) as RecyclerView
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
            apiCall = this.apiClient.service.requestDataNext()
            apiCall.enqueue(object : Callback<JSONobject> {

                override fun onResponse(call : Call<JSONobject>, response : Response<JSONobject>){
                    Log.d("gedebug",response.body().toString())
                    if(response.isSuccessful){
                        objek= response.body()!!
                        items= objek.events as MutableList<Event>

                        RV = view.findViewById(R.id.rv_nextmatch) as RecyclerView
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
