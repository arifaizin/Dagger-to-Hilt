package com.arif.daggerhilt

import android.app.Application
import com.arif.daggerhilt.di.AppComponent
import com.arif.daggerhilt.di.DaggerAppComponent

open class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}