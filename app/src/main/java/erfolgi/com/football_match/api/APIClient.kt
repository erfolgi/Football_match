package erfolgi.com.football_match.api

import com.google.gson.GsonBuilder
import erfolgi.com.football_match.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class APIClient {
    val service: APICall
        get() = retrofit(BuildConfig.BASE_URL)!!.create(APICall::class.java)

    companion object {
        private var retrofit: Retrofit? = null

        fun retrofit(BASE_URL: String): Retrofit? {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            if (retrofit == null) {
                val gson = GsonBuilder()
                        .setLenient()
                        .create()
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(client)
                        .build()
            }
            return retrofit
        }
//        fun GetService(): APICall {
//            return retrofit(BuildConfig.BASE_URL)!!.create(APICall::class.java)
//        }
    }
//    fun GetService(): APICall {
////        InterfaceForCategory retrofit(BuildConfig.BASE_URL)!!.create(APICall::class.java)
////        val categoryService = retrofit.create(InterfaceForCategory::class.java)
//        return retrofit(BuildConfig.BASE_URL)!!.create(APICall::class.java)
//    }


}
