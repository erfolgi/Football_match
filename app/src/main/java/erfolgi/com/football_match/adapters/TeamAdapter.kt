package erfolgi.com.football_match.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import erfolgi.com.football_match.R
import erfolgi.com.football_match.activities.TeamActivity
import erfolgi.com.football_match.models.Team

class TeamAdapter (private val context: Context, private val items: List<Team>)
    : RecyclerView.Adapter<TeamAdapter.TeamHolder>() {
    lateinit var mInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamHolder {
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemRow = mInflater.inflate(R.layout.item_team, parent, false)
        return TeamHolder(itemRow)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TeamHolder, position: Int) {
        holder.name.text = items[position].strTeam
        Glide.with(context).load(items[position].strTeamBadge).into(holder.image)
        holder.bind(context,items[position].idTeam.toString(), items[position].strTeam.toString())
    }

    class TeamHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.image_team)
        var name= itemView.findViewById<TextView>(R.id.text_team)

        fun bind(con: Context, ID:String,NAME:String) {

            itemView.setOnClickListener {

                val intent = Intent (con, TeamActivity::class.java)
                intent.putExtra(TeamActivity.EXTRA_ID_TEAM, ID)
                Log.e("qqqqqq",NAME)
                intent.putExtra(TeamActivity.EXTRA_NAME, NAME)
                itemView.context.startActivity(intent)
            }
        }
    }
}