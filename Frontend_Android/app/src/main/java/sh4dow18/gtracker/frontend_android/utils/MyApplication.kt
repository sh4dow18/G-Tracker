package sh4dow18.gtracker.frontend_android.utils

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createSessionManager(SessionManager(applicationContext))
    }

    companion object {
        var sessionManager: SessionManager? = null
        fun createSessionManager(newInstance: SessionManager){
            sessionManager = newInstance
        }
    }
}