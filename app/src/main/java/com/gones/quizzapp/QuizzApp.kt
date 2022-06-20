package com.gones.quizzapp

import android.app.Application
import com.gones.quizzapp.common.appModules
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class QuizzApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModules)
        }
    }
}