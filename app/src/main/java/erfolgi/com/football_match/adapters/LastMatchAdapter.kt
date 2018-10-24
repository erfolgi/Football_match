package erfolgi.com.football_match.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import erfolgi.com.football_match.R
import erfolgi.com.football_match.activities.DetailActivity

import erfolgi.com.football_match.models.Event


class LastMatchAdapter (private val context: Context, private val items: List<Event>, private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<LastMatchAdapter.LMHolder>() {
    lateinit var mInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LMHolder {
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemRow = mInflater.inflate(R.layout.item_match, parent, false)
        return LMHolder(itemRow)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: LMHolder, position: Int) {
        holder.datematch.text = items[position].eventDate
        holder.homename.text = items[position].homeTeam
        holder.homescore.text = items[position].homeScore
        holder.awayname.text = items[position].awayTeam
        holder.awayscore.text = items[position].awayScore
        holder.bind(context,items[position].eventId.toString())

    }

    class LMHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
       var datematch = itemView!!.findViewById<TextView>(R.id.date)
        var homename= itemView!!.findViewById<TextView>(R.id.teamA)
        var homescore= itemView!!.findViewById<TextView>(R.id.scoreA)
        var awayname= itemView!!.findViewById<TextView>(R.id.teamB)
        var awayscore= itemView!!.findViewById<TextView>(R.id.scoreB)
       //lateinit var id:String

        fun bind(con:Context,ID:String) {

            itemView.setOnClickListener {

                val intent = Intent (TabbedActivity@con, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, ID)
                itemView.context.startActivity(intent)
            }
        }
    }
}