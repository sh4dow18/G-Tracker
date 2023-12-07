package sh4dow18.gtracker.frontend_android.services

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sh4dow18.gtracker.frontend_android.utils.AuthorizationInterceptor
import java.util.concurrent.TimeUnit

object ServiceBuilder {
    private var gson: Gson = GsonBuilder()
        .setDateFormat("dd/MM/yyyy")
        .create()

    // If you need to check your request change the Level
    private var loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.NONE
    )

    // Increased the time out to the server
    private val client =
        OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(AuthorizationInterceptor()).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://g-tracker.onrender.com")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}