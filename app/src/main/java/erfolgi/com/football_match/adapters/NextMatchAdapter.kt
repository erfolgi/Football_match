package erfolgi.com.football_match.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.view.menu.MenuView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import erfolgi.com.football_match.R
import erfolgi.com.football_match.activities.DetailActivity
import erfolgi.com.football_match.models.Event
import java.text.SimpleDateFormat
import java.util.*
import android.support.v4.content.ContextCompat.startActivity



class NextMatchAdapter (private val context: Context, private val items: List<Event>, private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<NextMatchAdapter.LMHolder>() {
    lateinit var mInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LMHolder {
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemRow = mInflater.inflate(R.layout.item_next_match, parent, false)
        return LMHolder(itemRow)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: LMHolder, position: Int) {

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
        val formatCalDate = SimpleDateFormat("dd-MM-yyyy")
        val caldate = formatCalDate.format(timeconvert)

        val datime= "$caldate$time"
        holder.datematch.text = date
        holder.timematch.text = time

        holder.calendar.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.time = SimpleDateFormat("dd-MM-yyyyhh:mm").parse(datime)
            val intent = Intent(Intent.ACTION_EDIT)
            intent.type = "vnd.android.cursor.item/event"
            intent.putExtra("beginTime", cal.timeInMillis)
            intent.putExtra("allDay", false)
            intent.putExtra("endTime", cal.timeInMillis + 110 * 60 * 1000)
            intent.putExtra("title", items[0].strEvent)
            context.startActivity(intent)
        }
    }
    private fun toGMTFormat(date: String?, time: String?): Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime = "$date $time"
        return formatter.parse(dateTime)
    }

    class LMHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var datematch = itemView.findViewById<TextView>(R.id.date_next)
        var homename= itemView.findViewById<TextView>(R.id.teamA_next)
        var homescore= itemView.findViewById<TextView>(R.id.scoreA_next)
        var awayname= itemView.findViewById<TextView>(R.id.teamB_next)
        var awayscore= itemView.findViewById<TextView>(R.id.scoreB_next)
        var timematch = itemView.findViewById<TextView>(R.id.time_next)
        var calendar = itemView.findViewById<ImageButton>(R.id.calendar_next)
        //lateinit var id:String

        fun bind(con: Context, ID:String) {

            itemView.setOnClickListener {

                val intent = Intent (con, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, ID)
                itemView.context.startActivity(intent)
            }
        }
    }
}