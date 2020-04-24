package ru.sequentor.uula.application

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {

        lateinit var mInstance: App

        fun getContext(): Context {
            return mInstance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }
}