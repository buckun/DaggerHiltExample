package com.example.daggerhiltexample.utils

import com.example.daggerhiltexample.MyApplication
import com.example.daggerhiltexample.data.datasource.RemoteDataSource
import com.example.daggerhiltexample.data.network.ServiceApi
import com.example.daggerhiltexample.data.repository.Repository
import com.example.daggerhiltexample.fa.HomeViewModelFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppContainer(myApplication: MyApplication) {

    private val okHttp = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    private val gsonConverterFactory: GsonConverterFactory = GsonConverterFactory.create()

    val retrofit: ServiceApi = Retrofit.Builder()
        .client(okHttp)
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .build()
        .create(ServiceApi::class.java)

    val remoteData = RemoteDataSource(retrofit)
    val repository = Repository(remoteData)

    val fa = HomeViewModelFactory(repository, myApplication)

}