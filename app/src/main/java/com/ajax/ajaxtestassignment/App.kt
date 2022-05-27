package com.ajax.ajaxtestassignment

import android.app.Application
import com.ajax.ajaxtestassignment.di.GlobalFactory

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalFactory.init(this)
    }
}