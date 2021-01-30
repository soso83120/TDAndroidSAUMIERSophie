package com.example.tdandroidsaumiersophie.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlin.math.max
import com.example.tdandroidsaumiersophie.network.Dish
import com.example.tdandroidsaumiersophie.R
import com.example.tdandroidsaumiersophie.basket.BasketItem
import com.example.tdandroidsaumiersophie.databinding.ActivityDetailBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    companion object {
        const val DISH_EXTRA = "DISH_EXTRA"
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
    }

    private fun refreshShop(dish: Dish) {
        val price = itemCount * dish.prices.first().price.toFloat()
        binding.itemCount.text = itemCount.toString()
        binding.shopButton.text = "${getString(R.string.total)} $price€"
        addToBasket(dish, itemCount)
    }

    private fun addToBasket(dish: Dish, count: Int) {
        // Recuperer le basket du fichier
        // si il existe pas, le créer
        // mettre à jour le basket avec notre basket item
        // En vérifiant basketitem similaire
        val item = BasketItem(dish, count)
        val json = GsonBuilder().create().toJson(item)
        Log.d("basket", json)
    }
}

