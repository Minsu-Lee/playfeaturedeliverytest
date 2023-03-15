package com.jackson.playfeatureresourcesample

import android.app.Application
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat

class MyApplication: Application() {

    companion object {
        private var _instance: MyApplication? = null
        val globalApplicationContext: MyApplication get() = _instance!!
        const val FEATURE_PACKAGE = "com.jackson.dynamicfeature"
    }

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}