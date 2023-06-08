package com.example.daggerhiltexample.data.network

import com.example.daggerhiltexample.model.ProductsItem
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Path

interface ServiceApi {
    @GET("developer-application-test/cart/list")
    suspend fun getResult(): Response<com.example.daggerhiltexample.model.Response>

    @GET("developer-application-test/cart/{product_id}/detail")
    suspend fun getDetails(
        @Path("product_id") product_id: String
    ): Response<ProductsItem>
}