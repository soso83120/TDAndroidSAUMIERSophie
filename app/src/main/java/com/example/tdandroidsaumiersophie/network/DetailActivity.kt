package com.example.tdandroidsaumiersophie.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tdandroidsaumiersophie.Category.CategoryAdapter
import com.example.tdandroidsaumiersophie.network.Dish
import com.example.tdandroidsaumiersophie.R
import com.example.tdandroidsaumiersophie.databinding.ActivityDetailBinding
import com.example.tdandroidsaumiersophie.databinding.DishesCellBinding
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    companion object {
        const val DISH_EXTRA = "DISH_EXTRA"
    }
    lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dish =intent.getSerializableExtra(DISH_EXTRA) as? Dish
        // si jamais dish est null notre applis crache dish!!.prices
        binding.ingredientsName.text = dish?.ingredients?.map{it.name}?.joinToString(",")

    }

}