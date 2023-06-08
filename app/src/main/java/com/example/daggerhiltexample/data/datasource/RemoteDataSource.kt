package com.example.daggerhiltexample.data.datasource

import com.example.daggerhiltexample.data.network.ServiceApi
import com.example.daggerhiltexample.model.ProductsItem
import retrofit2.Response
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val serviceApi : ServiceApi
) {
    suspend fun fetchResult(): Response<com.example.daggerhiltexample.model.Response> {
        return serviceApi.getResult()
    }

    suspend fun fetchDetails(productId : String): Response<ProductsItem> {
        return serviceApi.getDetails(productId)
    }
}