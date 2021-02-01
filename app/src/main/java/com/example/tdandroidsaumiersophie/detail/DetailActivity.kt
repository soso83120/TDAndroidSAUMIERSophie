package com.example.tdandroidsaumiersophie.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.tdandroidsaumiersophie.Category.CategoryActivity
import kotlin.math.max
import com.example.tdandroidsaumiersophie.network.Dish
import com.example.tdandroidsaumiersophie.R
import com.example.tdandroidsaumiersophie.basket.Basket
import com.example.tdandroidsaumiersophie.basket.BasketItem
import com.example.tdandroidsaumiersophie.databinding.ActivityDetailBinding
import com.example.tdandroidsaumiersophie.network.HomeActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    companion object {
        const val DISH_EXTRA="DISH_EXTRA"
        const val ITEM_COUNT="ITEM_COUNT"
        const val USER_PREFERENCES_NAME="USER_PREFERENCES_NAME"
    }


    lateinit var binding: ActivityDetailBinding
    private var itemCount = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dish = intent.getSerializableExtra(DISH_EXTRA) as? Dish
        dish?.let {
            setupView(it)
        }
    }

    private fun setupView(dish: Dish) {
        binding.titreplat.text = dish.name
        binding.ingredientsName.text = dish.ingredients.map { it.name }?.joinToString(", ")
        binding.viewPager.adapter = PhotoAdapter(this, dish.images)
        refreshShop(dish)

        binding.less.setOnClickListener {
            itemCount = max(1, itemCount - 1)
            refreshShop(dish)
        }

        binding.more.setOnClickListener {
            itemCount += 1
            refreshShop(dish)
        }
        binding.shopButton.setOnClickListener {
            addToBasket(dish, itemCount)
        }
    }

    private fun refreshShop(dish: Dish) {
        val price = itemCount * dish.prices.first().price.toFloat()
        binding.itemCount.text = itemCount.toString()
        binding.shopButton.text = "${getString(R.string.total)} $price€"

    }

    private fun addToBasket(dish: Dish, count: Int) {
        // Recuperer le basket du fichier
        // si il existe pas, le créer
        // mettre à jour le basket avec notre basket item
        // En vérifiant basketitem similaire
        var basket = Basket.getBasket(this)
        basket.addItem(BasketItem(dish, count))
        basket.save(this)
        val item = BasketItem(dish, count)
        val json = GsonBuilder().create().toJson(item)
        Log.d("basket", json)
        //Snacbar
       //  Snackbar.make(binding.root, getString(R.string.basket),Snackbar.LENGTH_LONG).show()
        //popup
        var builder= AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.basket))
        builder.setPositiveButton("OK"){dialog, which->

        }
        builder.show()
    }
    private fun refreshMenu(basket: Basket){

        val count=basket.itemsCount
        val sharedPreferences=getSharedPreferences(DetailActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor= sharedPreferences.edit()
        editor.putInt(ITEM_COUNT,count)
        editor.apply()
        invalidateOptionsMenu() //refresh l'affichage du menu

    }

}

