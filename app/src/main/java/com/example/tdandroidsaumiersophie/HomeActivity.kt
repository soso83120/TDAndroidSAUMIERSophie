package com.example.tdandroidsaumiersophie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Button
import com.example.tdandroidsaumiersophie.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button4.setOnClickListener {
            intent.putExtra(CATEGORY_NAME, getString(R.string.appetizer))
            Toast.makeText(applicationContext, "appetizer", Toast.LENGTH_LONG).show()
            statCategoryActivity(ItemType.STARTER)
        }

        binding.button6.setOnClickListener {
            intent.putExtra(CATEGORY_NAME, getString(R.string.main_course))
            Toast.makeText(applicationContext, "main_course", Toast.LENGTH_LONG).show()
            statCategoryActivity(ItemType.MAIN)
        }

        binding.button7.setOnClickListener {
            intent.putExtra(CATEGORY_NAME, getString(R.string.dessert))
            Toast.makeText(applicationContext, "dessert", Toast.LENGTH_LONG).show()
            statCategoryActivity(ItemType.DESSERT)
        }

    }

    private fun statCategoryActivity(item: ItemType) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CATEGORY_NAME, item)
        startActivity(intent)
    }

    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }
}