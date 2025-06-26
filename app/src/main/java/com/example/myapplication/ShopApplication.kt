package com.example.myapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.cux.analytics_sdk.CuxAnalytics

@HiltAndroidApp
class ShopApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        CuxAnalytics.init(this, "1902166147.0.60686300.1750841890.685bba2294296")
    }
}