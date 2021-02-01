package com.example.tdandroidsaumiersophie.basket

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tdandroidsaumiersophie.R
import com.example.tdandroidsaumiersophie.databinding.ActivityBasketBinding
lateinit var binding: ActivityBasketBinding
private lateinit var basket: Basket
//class BasketActivity (basket: Basket): RecyclerView.adapter<BasketAdapter.BasketViewHoilde>() {
    class BasketActivity : AppCompatActivity()/*, BasketCellInterface */ {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        basket=Basket.getBasket(this)

        reloadData()
    }

    private fun reloadData() {
        binding.basketRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.basketRecyclerView.adapter = BasketAdapter(basket, this) {
            basket.items.remove(it)
            basket.save(this)
            reloadData()
        }
    }
}