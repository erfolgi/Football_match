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
import erfolgi.com.football_match.adapters.FavPlayerAdapter
import erfolgi.com.football_match.db.database
import erfolgi.com.football_match.db.table.PlayerTable
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh


class FavoritePlayerFragment : Fragment() {
    lateinit var RV: RecyclerView
    lateinit var favadapter: FavPlayerAdapter
    private var items: MutableList<PlayerTable> = mutableListOf()
    lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onResume() {
        super.onResume()
        items.clear()
        showFavorite()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_favorite_player, container, false)
        swipeRefresh=view.findViewById(R.id.SR_Fav_Player)
        RV = view.findViewById(R.id.rv_favorite_Player) as RecyclerView
        RV.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        RV.layoutManager = LinearLayoutManager(context)
        favadapter = FavPlayerAdapter(context, items)
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
            val result = select(PlayerTable.TABLE_PLAYER)
            val favorite = result.parseList(classParser<PlayerTable>())
            items.addAll(favorite)
            favadapter.notifyDataSetChanged()

        }
    }


}
