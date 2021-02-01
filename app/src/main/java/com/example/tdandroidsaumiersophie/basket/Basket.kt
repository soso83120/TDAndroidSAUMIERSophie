package com.example.tdandroidsaumiersophie.basket

import android.content.Context
import com.example.tdandroidsaumiersophie.databinding.ActivityBasketBinding
import com.example.tdandroidsaumiersophie.detail.DetailActivity
import com.example.tdandroidsaumiersophie.network.Dish
import com.google.gson.GsonBuilder
import java.io.File
import java.io.Serializable

// serializable la rendre encodable et décodable
class Basket(val items: MutableList<BasketItem>, context: Context): Serializable{
    var itemsCount: Int=0
        get(){
            if(items.count()> 0){
                return items
                    .map{ it.count }
                    .reduce { acc, i -> acc + i}
            }
            else{
                return 0
            }

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
        updatecount(context)
    }
    private fun updatecount(context: Context)
    {
        val sharedPreferences=context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor= sharedPreferences.edit()
        editor.putInt(ITEM_COUNT, itemsCount)
        editor.apply()
    }

    companion object {
        const val ITEM_COUNT="ITEM_COUNT"
        const val USER_PREFERENCES_NAME="USER_PREFERENCES_NAME"
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