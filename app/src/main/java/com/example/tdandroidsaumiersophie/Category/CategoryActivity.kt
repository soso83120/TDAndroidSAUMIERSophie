package com.example.tdandroidsaumiersophie.Category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Response
import com.example.tdandroidsaumiersophie.*
import com.android.volley.toolbox.StringRequest
import com.example.tdandroidsaumiersophie.databinding.ActivityCategoryBinding
import com.example.tdandroidsaumiersophie.network.HomeActivity
import com.example.tdandroidsaumiersophie.network.MenuResult
import com.example.tdandroidsaumiersophie.network.NetworkConstant
import com.example.tdandroidsaumiersophie.Category.CategoryActivity
import com.example.tdandroidsaumiersophie.Category.CategoryAdapter
import com.example.tdandroidsaumiersophie.network.Dish
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
class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val selectedItem= intent.getSerializableExtra(HomeActivity.CATEGORY_NAME) as? ItemType

        binding.categoryTitle.text= getCategoryTitle(selectedItem)

        makeRequest(selectedItem)
        Log.d("lifecycle", "onCreate")
    }

    private fun makeRequest(selectedItem: ItemType?) {
        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_MENU

        val jsonData = JSONObject()
        jsonData.put(NetworkConstant.ID_SHOP, "1")

        var request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            { response ->
                val menuResult = GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)
                val items = menuResult.data.firstOrNull { it.name == ItemType.categoryTitle(selectedItem) }
                loadList(items?.items)
            },
            { error ->
                error.message?.let {
                    Log.d("request", it)
                } ?: run {
                    Log.d("request", error.toString())
                }
            }
        )
        queue.add(request)

        /*
// Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                Log.d("request", response)
            },
            Response.ErrorListener {
                Log.d("request", "not working ${it.localizedMessage}")
            })
*/
// Add the request to the RequestQueue.
        queue.add(request)
    }
    private fun loadList(dishes: List<Dish>?) {
        dishes?.let {
            val adapter = CategoryAdapter(it) { dish ->
                Log.d("dish", "selected dish ${dish.name}")
            }
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = adapter
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



}