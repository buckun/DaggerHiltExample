package com.example.daggerhiltexample

import android.app.Application
import com.example.daggerhiltexample.utils.AppContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
    val appContainer = AppContainer(this)

}