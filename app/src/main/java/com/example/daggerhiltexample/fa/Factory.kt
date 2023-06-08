package com.example.daggerhiltexample.fa

import android.app.Application
import com.example.daggerhiltexample.data.repository.Repository
import com.example.daggerhiltexample.ui.home.HomeViewModel

interface Factory<T> {
    fun create(): T
}


class HomeViewModelFactory(val repository: Repository, val application: Application) : Factory<HomeViewModel> {
    override fun create(): HomeViewModel {
       return HomeViewModel(repository, application )
    }

}