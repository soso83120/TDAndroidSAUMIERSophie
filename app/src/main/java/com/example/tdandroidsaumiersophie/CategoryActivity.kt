package com.example.tdandroidsaumiersophie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.example.tdandroidsaumiersophie.databinding.ActivityCategoryBinding

enum class ItemType {
    STARTER, MAIN, DESSERT
}
class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val selectedItem= intent.getSerializableExtra(HomeActivity.CATEGORY_NAME) as? ItemType
        binding.categoryTitle.text= getCategoryTitle(selectedItem)


        Log.d("lifecycle", "onCreate")
    }
    private fun getCategoryTitle(item: ItemType?): String {
        return when(item) {
            ItemType.STARTER -> getString(R.string.appetizer)
            ItemType.MAIN -> getString(R.string.main_course)
            ItemType.DESSERT -> getString(R.string.dessert)
            else -> ""
        }
    }
    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("lifecycle", "onRestart")
    }

    override fun onDestroy() {
        Log.d("lifecycle", "onDestroy")
        Toast.makeText(applicationContext, "destroypreviousactivity", Toast.LENGTH_LONG).show()
        super.onDestroy()
    }



}