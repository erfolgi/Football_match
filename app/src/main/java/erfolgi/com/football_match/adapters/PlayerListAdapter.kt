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
import erfolgi.com.football_match.activities.PlayerDetailActivity
import erfolgi.com.football_match.models.Player

class PlayerListAdapter (private val context: Context, private val items: List<Player>)
    : RecyclerView.Adapter<PlayerListAdapter.PlayerHolder>() {
    lateinit var mInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemRow = mInflater.inflate(R.layout.item_player, parent, false)
        return PlayerHolder(itemRow)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        holder.bind(context,items[position].idPlayer.toString())
        holder.name.text = items[position].strPlayer
        holder.role.text = items[position].strPosition
        if(items[position].strCutout!=null){
            Glide.with(context).load(items[position].strCutout).into(holder.image)
        }


    }

    class PlayerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var image: ImageView
        lateinit var name: TextView
        lateinit var role: TextView

        fun bind(con: Context, ID:String) {
            image= itemView.findViewById<ImageView>(R.id.iv_player)
            name = itemView.findViewById<TextView>(R.id.tv_player_name)
            role= itemView.findViewById<TextView>(R.id.tv_player_role)

            itemView.setOnClickListener {

                val intent = Intent (con, PlayerDetailActivity::class.java)
                intent.putExtra(PlayerDetailActivity.EXTRA_ID_PLAYER, ID)
                itemView.context.startActivity(intent)
            }
        }
    }
}