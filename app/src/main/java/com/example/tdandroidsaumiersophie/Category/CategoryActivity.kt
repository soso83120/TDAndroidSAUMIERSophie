package com.example.tdandroidsaumiersophie.Category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.tdandroidsaumiersophie.*
import com.example.tdandroidsaumiersophie.databinding.ActivityCategoryBinding
import com.example.tdandroidsaumiersophie.detail.DetailActivity
import com.example.tdandroidsaumiersophie.network.*
import com.example.tdandroidsaumiersophie.utils.Loader
import com.google.gson.GsonBuilder
import org.json.JSONObject


enum class ItemType {
    STARTER, MAIN, DESSERT;
    companion object {
        fun categoryTitle(item: ItemType?) : String {
            return when(item) {
                STARTER -> "Entrées"
                MAIN -> "Plats"
                DESSERT -> "Desserts"
                else -> ""
            }
        }
    }
}
class CategoryActivity : BaseActivity(){
    private lateinit var binding: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val selectedItem= intent.getSerializableExtra(HomeActivity.CATEGORY_NAME) as? ItemType
        binding.swipeLayout.setOnRefreshListener {
            resetCache()
            makeRequest(selectedItem)
        }
        binding.categoryTitle.text= getCategoryTitle(selectedItem)
        loadList(listOf<Dish>())
        makeRequest(selectedItem)
        Log.d("lifecycle", "onCreate")
    }

    private fun makeRequest(selectedItem: ItemType?) {
        resultFromCache()?.let{
            parseResult(it,selectedItem)
        }?: run {
            val loader = Loader()
            loader.show(this,"récupération enu")

            val queue = Volley.newRequestQueue(this)
            val url = Neworkconst.BASE_URL + Neworkconst.PATH_MENU
            val jsonData = JSONObject()
            jsonData.put(Neworkconst.ID_SHOP, "1")

            var request = JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonData,
                { response ->
                    loader.hide(this)
                    binding.swipeLayout.isRefreshing=false
                    val menuResult = GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)
                    val items = menuResult.data.firstOrNull { it.name == ItemType.categoryTitle(selectedItem) }
                    loadList(items?.items)
                },
                { error ->
                    loader.hide(this)
                    binding.swipeLayout.isRefreshing=false
                    error.message?.let {
                        Log.d("request", it)
                    } ?: run {
                        Log.d("request", error.toString())
                    }
                }
            )
            queue.add(request)
        }


    }

    private fun cacheResult(response: String){
        val sharedPreferences=getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor= sharedPreferences.edit()
        editor.putString(REQUEST_CACHE,response)
        editor.apply()
    }
    private fun resetCache() {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(REQUEST_CACHE)
        editor.apply()
    }
    private fun resultFromCache():String?{
        val sharedPreferences=getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(REQUEST_CACHE,null)
    }
    private fun parseResult(response: String, selectedItem: ItemType?){
        val menuResult=GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)
        val items=menuResult.data.firstOrNull{ it.name == ItemType.categoryTitle(selectedItem)}
        loadList( items?.items)
    }
    private fun loadList(dishes: List<Dish>?) {
        dishes?.let {
            val adapter = CategoryAdapter(it) { dish ->
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra(DetailActivity.DISH_EXTRA, dish)
                startActivity(intent)
            }
            binding.basketRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.basketRecyclerView.adapter = adapter
        }
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

companion object {
    const val USER_PREFERENCES_NAME="USER_PREFERNCES_NAME"
    const val REQUEST_CACHE="REQUEST_CACHE"
}

}


