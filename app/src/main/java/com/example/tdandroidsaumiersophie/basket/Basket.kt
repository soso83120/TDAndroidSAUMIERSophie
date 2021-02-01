package com.example.tdandroidsaumiersophie.basket

import android.content.Context
import com.example.tdandroidsaumiersophie.network.Dish
import com.google.gson.GsonBuilder
import java.io.File
import java.io.Serializable

// serializable la rendre encodable et décodable
class Basket(val items: MutableList<BasketItem>, context: Context): Serializable{
    var itemsCount: Int=0
        get(){
        return items
                .map{ it.count }
                .reduce { acc, i -> acc + i}
    }
    val jsonFile= File(context.cacheDir.absolutePath +BASKETFILE)
    fun addItem(item: BasketItem){
        var existingItem: BasketItem? = null
                existingItem= items.firstOrNull{
                    it.dish.name== item.dish.name
                }
        // on modifie juste le compte
        existingItem?.let {
            existingItem.count += item.count

        } ?: run{
            items.add(item)
        }
    }
    fun save(context: Context){
    val jsonFile=File(context.cacheDir.absolutePath + BASKETFILE)
        jsonFile.writeText(GsonBuilder().create().toJson(this))
    }


    companion object {
        fun getBasket(context: Context): Basket {
            val jsonFile = File(context.cacheDir.absolutePath + BASKETFILE)
            return if (jsonFile.exists()) {
                val json=jsonFile.readText()
                GsonBuilder().create().fromJson(json,Basket::class.java)
            } else {
                Basket(mutableListOf(), context)
            }
        }

        const val BASKETFILE="basket.json"
    }

}
class BasketItem(val dish: Dish, var count: Int): Serializable