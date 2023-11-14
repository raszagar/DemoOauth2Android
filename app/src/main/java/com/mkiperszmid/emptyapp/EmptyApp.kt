package com.mkiperszmid.emptyapp

import android.app.Application

class EmptyApp : Application() {
    companion object {
        var instance: EmptyApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}