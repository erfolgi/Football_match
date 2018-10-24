package erfolgi.com.football_match.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class JSONobject {

    @SerializedName("events")
    @Expose
    var events: List<Event>? = null

}