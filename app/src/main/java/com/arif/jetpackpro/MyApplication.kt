package com.arif.jetpackpro

import android.app.Application
import com.arif.jetpackpro.di.AppComponent
import com.arif.jetpackpro.di.DaggerAppComponent

open class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}