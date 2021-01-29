package com.example.tdandroidsaumiersophie.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tdandroidsaumiersophie.R

class DetailActivity : AppCompatActivity() {
    companion object {
        const val DISH_EXTRA = "DISH_EXTRA"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        intent.getSerializableExtra(DISH_EXTRA) as? Dish
    }
}