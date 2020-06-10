package com.pratclot.ranchcontrol

import android.app.Application
import com.facebook.stetho.Stetho
import com.pratclot.ranchcontrol.di.AppComponent
import com.pratclot.ranchcontrol.di.DaggerAppComponent

class RanchControl : Application() {
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext, this)
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(applicationContext)
    }
}
