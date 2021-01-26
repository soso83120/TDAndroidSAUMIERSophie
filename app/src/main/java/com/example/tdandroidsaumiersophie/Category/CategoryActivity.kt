package com.example.tdandroidsaumiersophie.Category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.tdandroidsaumiersophie.*
import com.example.tdandroidsaumiersophie.databinding.ActivityCategoryBinding
import com.example.tdandroidsaumiersophie.network.HomeActivity
import com.example.tdandroidsaumiersophie.network.MenuResult
import com.example.tdandroidsaumiersophie.network.NetworkConstant
import com.google.gson.GsonBuilder
import org.json.JSONObject


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
        //loadList()
        makeRequest()
        Log.d("lifecycle", "onCreate")
    }

    private fun makeRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_MENU

        val jsonData = JSONObject()
        jsonData.put(NetworkConstant.ID_SHOP, "1")

        var request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            { response ->
                Log.d("request", response.toString(2))
                val menuResult = GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)
                menuResult.data.forEach {
                    Log.d("request", it.name)
                }
            },
            { error ->
                error.message?.let {
                    Log.d("request", it)
                } ?: run {
                    Log.d("request", error.toString())
                }
            }
        )

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
    private fun loadList() {
        var entries = listOf<String>("salade", "boeuf", "glace")
        val adapter =
            CategoryAdapter(entries)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
    private fun getCategoryTitle(item: ItemType?): String {
        return when(item) {
            ItemType.STARTER -> getString(
                R.string.appetizer
            )
            ItemType.MAIN -> getString(
                R.string.main_course
            )
            ItemType.DESSERT -> getString(
                R.string.dessert
            )
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