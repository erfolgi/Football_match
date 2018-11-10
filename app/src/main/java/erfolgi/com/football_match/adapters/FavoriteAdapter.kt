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
import erfolgi.com.football_match.models.Favorites
import java.text.SimpleDateFormat
import java.util.*

class FavoriteAdapter(private val context: Context?, private val items: List<Favorites>, private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<FavoriteAdapter.FavHolder>(){
    lateinit var mInflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.FavHolder {
        mInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemRow = mInflater.inflate(R.layout.item_match, parent, false)
        return FavoriteAdapter.FavHolder(itemRow)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.FavHolder, position: Int) {
        holder.homename.text = items[position].homeTeam
        holder.homescore.text = items[position].homeScore
        holder.awayname.text = items[position].awayTeam
        holder.awayscore.text = items[position].awayScore
        holder.bind(context,items[position].eventId.toString())
        val timeconvert = toGMTFormat(items[position].eventDate,items[position].eventTime)
        val formatDate = SimpleDateFormat("E, dd MM yyyy")
        val formatTime = SimpleDateFormat("HH:mm")
        val date = formatDate.format(timeconvert)
        val time = formatTime.format(timeconvert)
        holder.datematch.text = date
        holder.timematch.text = time
    }
    private fun toGMTFormat(date: String?, time: String?): Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime = "$date $time"
        return formatter.parse(dateTime)
    }

    class FavHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var datematch = itemView.findViewById<TextView>(R.id.date)
        var timematch = itemView.findViewById<TextView>(R.id.time)
        var homename= itemView.findViewById<TextView>(R.id.teamA)
        var homescore= itemView.findViewById<TextView>(R.id.scoreA)
        var awayname= itemView.findViewById<TextView>(R.id.teamB)
        var awayscore= itemView.findViewById<TextView>(R.id.scoreB)
        //lateinit var id:String

        fun bind(con:Context?,ID:String) {

            itemView.setOnClickListener {

                val intent = Intent (con, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, ID)
                itemView.context.startActivity(intent)
            }
        }
    }
}