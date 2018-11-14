package erfolgi.com.football_match.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import erfolgi.com.football_match.R
import erfolgi.com.football_match.adapters.FavTeamAdapter
import erfolgi.com.football_match.db.database
import erfolgi.com.football_match.db.table.TeamTable
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoriteTeamFragment : Fragment() {
    lateinit var RV: RecyclerView
    lateinit var favadapter: FavTeamAdapter
    private var items: MutableList<TeamTable> = mutableListOf()
    lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onResume() {
        super.onResume()
        items.clear()
        showFavorite()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_favorite_team, container, false)
        swipeRefresh=view.findViewById(R.id.SR_Fav_Team)
        RV = view.findViewById(R.id.rv_favorite_team) as RecyclerView
        RV.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        RV.layoutManager = LinearLayoutManager(context)
        favadapter = FavTeamAdapter(context, items)
        RV.adapter = favadapter
        items.clear()
        showFavorite()
        swipeRefresh.onRefresh {
            items.clear()
            showFavorite()
            swipeRefresh.isRefreshing = false
        }
        return view
    }
    private fun showFavorite(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(TeamTable.TABLE_TEAM)
            val favorite = result.parseList(classParser<TeamTable>())
            items.addAll(favorite)
            favadapter.notifyDataSetChanged()

        }
    }


}
