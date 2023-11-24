package sh4dow18.gtracker.frontend_android.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(
        "G-Tracker App", Context.MODE_PRIVATE)

    companion object{
        const val USER_TOKEN = "user_token"
        var user_email = ""
    }

    fun saveAuthToken(token: String){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthToken() : String?{
        return prefs.getString(USER_TOKEN, null)
    }

    fun deleteAuthToken(){
        val editor = prefs.edit()
        editor.remove(USER_TOKEN)
        editor.apply()
    }

    fun setUserEmail(email: String) {
        user_email = email
    }

    fun getUserEmail(): String {
        return user_email
    }
}