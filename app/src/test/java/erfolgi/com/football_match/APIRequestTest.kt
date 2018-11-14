package erfolgi.com.football_match

import erfolgi.com.football_match.api.APIClient
import erfolgi.com.football_match.models.*
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
    fun requestLastMatchTest() {
        var objek: JSONobject?=null
        val apiClient = APIClient()
        val apiCall = apiClient.service.requestDataLast()
        try {
            val response=apiCall.execute()
            objek = response.body()
            val items= objek?.events as MutableList<Event>
            assertTrue(response.isSuccessful && items[0].eventId!=null)
            //Assert.assertEquals("576577", items[0].eventId)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    @Test
    fun requestNextMatchTest() {
        var objek: JSONobject?=null
        val apiClient = APIClient()
        val apiCall = apiClient.service.requestDataNext()
        try {
            val response=apiCall.execute()
            objek = response.body()
            val items= objek?.events as MutableList<Event>
            assertTrue(response.isSuccessful && items[0].eventId!=null)
            //Assert.assertEquals("576577", items[0].eventId)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    @Test
    fun requestTeamTest() {
        var objek: TeamObject?=null
        val apiClient = APIClient()
        val apiCall = apiClient.service.requestTeamByLeague("English Premier League")
        try {
            val response=apiCall.execute()
            objek = response.body()
            val items= objek?.teams as MutableList<Team>
            assertTrue(response.isSuccessful && items[0].idTeam!=null)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    @Test
    fun requestPlayerTest() {
        var objek: PlayerObject?=null
        val apiClient = APIClient()
        val apiCall = apiClient.service.requestAllPlayersByTeam("133604")
        try {
            val response=apiCall.execute()
            objek = response.body()
            val items= objek?.players as MutableList<Player>
            assertTrue(response.isSuccessful && items[0].idPlayer!=null)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}