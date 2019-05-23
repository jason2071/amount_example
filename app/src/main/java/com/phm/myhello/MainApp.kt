package com.phm.myhello

import android.app.Application
import com.phm.myhello.manager.Contextor

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Contextor.getInstance().init(applicationContext)
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}