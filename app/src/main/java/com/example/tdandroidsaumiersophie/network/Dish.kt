package com.example.tdandroidsaumiersophie.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Dish(
    @SerializedName("name_fr") val name: String,
    @SerializedName("ingredients") val ingredients: List<Ingredient>,
    @SerializedName("images") val images: List<String>,
    @SerializedName("prices") val prices: List<Price>
): Serializable {

}