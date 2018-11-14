package erfolgi.com.football_match.fragments


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import erfolgi.com.football_match.R
import erfolgi.com.football_match.adapters.LastMatchAdapter
import erfolgi.com.football_match.api.APIClient
import erfolgi.com.football_match.models.Event
import erfolgi.com.football_match.models.JSONobject
import kotlinx.android.synthetic.main.activity_tabbed.view.*
import kotlinx.android.synthetic.main.fragment_last_match.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.support.v4.ctx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v4.view.MenuItemCompat.getActionView

import android.support.v7.widget.SearchView
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE


class LastMatchFragment : Fragment() {
    private val apiClient = APIClient()
    lateinit var apiCall: Call<JSONobject>
    lateinit var RV: RecyclerView
    lateinit var lmadapter: LastMatchAdapter
    private var items: MutableList<Event> = mutableListOf()
    var objek:JSONobject?=null
    lateinit var swipeRefresh:SwipeRefreshLayout
    lateinit var con: Context
    lateinit var spinner:Spinner
    var id_league="4328"
    private lateinit var searchView: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_last_match, container, false)
        swipeRefresh=view.findViewById(R.id.SR_Last)
        RV = view.findViewById(R.id.rv_lastmatch) as RecyclerView
        RV.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        RV.layoutManager = LinearLayoutManager(context)
        con= context as Context
        spinner=view.findViewById(R.id.spin_last)
        spinner.visibility=VISIBLE

        val spinnerID = resources.getStringArray(R.array.leagueID)

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                Log.d("array spin","isi"+spinnerItems[position])
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
        activity?.contentView?.bottom_navigation?.isEnabled=false

        apiCall = this.apiClient.service.requestLastByLeague(id)
        apiCall.enqueue(object : Callback<JSONobject> {

            override fun onResponse(call : Call<JSONobject>, response : Response<JSONobject> ){
                Log.d("gedebug",response.body().toString())

                if(response.isSuccessful){
                    objek= response.body()
                    lastfailed?.visibility=(View.INVISIBLE)
                    items= objek?.events as MutableList<Event>
                    lmadapter = LastMatchAdapter(con, items){

                    }
                    RV.adapter = lmadapter
                    swipeRefresh.isRefreshing = false
                }
            }

            @SuppressLint("SetTextI18n")
            override fun  onFailure(call :Call<JSONobject>, t: Throwable) {
                //Toast.makeText(context, "Data Tidak bisa diambil"+t, Toast.LENGTH_LONG).show();
                Log.e("gedebug",t.toString())
                swipeRefresh.isRefreshing = false

                lastfailed.visibility=(View.VISIBLE)//setvisibility(View.INVISIBLE);
                lastfailed.text="Connection Failed."
            }
        })
        activity?.contentView?.bottom_navigation?.isEnabled=true
    }

    fun SearchAPI(query:String){
        spinner.visibility=INVISIBLE
        items.clear()
        apiCall = this.apiClient.service.requestSearchMatch(query)
        apiCall.enqueue(object : Callback<JSONobject> {
            override fun onResponse(call : Call<JSONobject>, response : Response<JSONobject> ){
                Log.d("gedebug",response.body().toString())
                if(response.isSuccessful){
                    objek= response.body()
                    if (objek?.event!=null){
                        lastfailed?.visibility=(View.INVISIBLE)
                        items= objek?.event as MutableList<Event>
                        lmadapter = LastMatchAdapter(con, items){
                        }
                        RV.adapter = lmadapter
                    }
                    swipeRefresh.isRefreshing = false
                }
            }

            @SuppressLint("SetTextI18n")
            override fun  onFailure(call :Call<JSONobject>, t: Throwable) {
                //Toast.makeText(context, "Data Tidak bisa diambil"+t, Toast.LENGTH_LONG).show();
                Log.e("gedebug",t.toString())
                swipeRefresh.isRefreshing = false

                lastfailed.visibility=(View.VISIBLE)//setvisibility(View.INVISIBLE);
                lastfailed.text="Connection Failed."
            }
        })
    }



    override fun onPrepareOptionsMenu(menu: Menu) {
        val mSearchMenuItem = menu.findItem(R.id.action_search)
        searchView = mSearchMenuItem.actionView as SearchView

        MenuItemCompat.setOnActionExpandListener(mSearchMenuItem, object : MenuItem.OnActionExpandListener, MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                items.clear()
                spinner.visibility=VISIBLE
                LoadAPI(id_league)
                return true  // Return true to collapse action view
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                // Do something when expanded
                return true  // Return true to expand action view
            }
        })


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                if(query!=""){
                    swipeRefresh.isRefreshing = true
                    SearchAPI(query)
                }else{
                    spinner.visibility=VISIBLE
                    LoadAPI(id_league)
                }

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                if (newText!=""){
                    items.clear()
                    SearchAPI(newText)
                }else{
                    spinner.visibility=VISIBLE
                    LoadAPI(id_league)
                }
                return false
            }
        })
    }
}