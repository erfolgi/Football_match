package erfolgi.com.football_match.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import erfolgi.com.football_match.R
import erfolgi.com.football_match.adapters.PlayerListAdapter
import erfolgi.com.football_match.api.APIClient
import erfolgi.com.football_match.models.Player
import erfolgi.com.football_match.models.PlayerObject
import kotlinx.android.synthetic.main.activity_player_detail.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.support.v4.ctx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class PlayerDetailActivity : AppCompatActivity() {
    private val apiClient = APIClient()
    lateinit var apiCall: Call<PlayerObject>
    lateinit var items: MutableList<Player>
    var objek: PlayerObject?=null

    companion object {
        var EXTRA_ID_PLAYER="ID_PLAYER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        EXTRA_ID_PLAYER=intent.getStringExtra(EXTRA_ID_PLAYER)
        LoadAPI(EXTRA_ID_PLAYER)

    }
    private fun LoadAPI(id:String){

        apiCall = this.apiClient.service.requestDetailPlayerById(id)
        apiCall.enqueue(object : Callback<PlayerObject> {

            override fun onResponse(call : Call<PlayerObject>, response : Response<PlayerObject>){

                if(response.isSuccessful &&response.body()!=null){
                    objek= response.body()
                    items= objek?.playerss as MutableList<Player>
                    supportActionBar?.title = items[0].strPlayer
                    if (items[0].strHeight!=null||items[0].strHeight!="0"||items[0].strHeight!=""){
                        tv_height.text=items[0].strHeight
                    }else{
                        tv_height.text="N/A"
                    }
                    tv_weight.text="N/A"
                    var ab="abc"

                        ab += items[0].strWeight.toString()
                        ab=ab.replace("abc","")
                        if(ab==""||ab=="0"||ab==null){
                            tv_weight.text="N/A"
                        }else{
                            tv_weight.text=items[0].strWeight.toString()
                        }

                    Log.e("/|/", "milder: "+tv_weight.text as String?)

                    tv_player_desc.text=items[0].strDescriptionEN
                    tv_role.text=items[0].strPosition
                    Glide.with(this@PlayerDetailActivity).load(items[0].strFanart1).into(iv_player_fanart)
                }
            }

            @SuppressLint("SetTextI18n")
            override fun  onFailure(call :Call<PlayerObject>, t: Throwable) {
                Toast.makeText(ctx, "Data Tidak bisa diambil"+t, Toast.LENGTH_LONG).show()
            }
        })

    }
}
