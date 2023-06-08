package com.example.daggerhiltexample.data.repository

import com.example.daggerhiltexample.data.datasource.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
) {
    val remote = remoteDataSource
}