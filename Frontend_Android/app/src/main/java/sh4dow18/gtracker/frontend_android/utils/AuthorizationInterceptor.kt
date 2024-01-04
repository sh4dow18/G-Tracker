package sh4dow18.gtracker.frontend_android.utils

import android.util.Log
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        MyApplication.sessionManager?.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization", it)
        }
        return chain.proceed(requestBuilder.build())
    }
}