package com.example.tdandroidsaumiersophie.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Dish (
    @SerializedName("name_fr") val name: String,
    val ingredients: List<Ingredient>,
    val images: List<String>,
    val prices:List<Price>
): Serializable{

    fun getPrice() = prices[0].price + "€"
    fun getPriceItem() = prices[0].price
    fun getFormattedPrice() = prices[0].price + "€"
    fun getImage() = if (images.isNotEmpty() && images[0].isNotEmpty()) {
        images[0]
    } else {
        null
    }

    fun getAllPictures() = if (images.isNotEmpty() && images.any { it.isNotEmpty() }) {
        images.filter { it.isNotEmpty() }
    } else {
        null
    }

    fun getThumbnailUrl(): String? {
        return if (images.isNotEmpty() && images[0].isNotEmpty()) {
            images[0]
        } else {
            null
        }
    }
}
