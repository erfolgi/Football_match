package erfolgi.com.football_match

import erfolgi.com.football_match.api.APIClient
import erfolgi.com.football_match.models.Event
import erfolgi.com.football_match.models.JSONobject
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import retrofit2.adapter.rxjava2.Result.response



class APIRequestTest {
    @Test
    fun requestTest() {
        var objek: JSONobject?=null
        val apiClient = APIClient()
        val apiCall = apiClient.service.requestDataLast()
        try {
            val response=apiCall.execute()
            objek = response.body()
            val items= objek?.events as MutableList<Event>
            assertTrue(response.isSuccessful && items[0].eventId?.startsWith("576") ?:true)
            //Assert.assertEquals("576577", items[0].eventId)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
}