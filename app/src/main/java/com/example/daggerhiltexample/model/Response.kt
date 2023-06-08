package com.example.daggerhiltexample.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Response(

    @field:SerializedName("products")
    val products: List<ProductsItem> = emptyList()
) : Parcelable

@Parcelize
data class ProductsItem(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("product_id")
    val productId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null
) : Parcelable