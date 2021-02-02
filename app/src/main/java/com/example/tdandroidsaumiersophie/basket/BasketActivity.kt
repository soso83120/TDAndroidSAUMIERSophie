package com.example.tdandroidsaumiersophie.basket

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.tdandroidsaumiersophie.R
import com.example.tdandroidsaumiersophie.databinding.ActivityBasketBinding
import com.example.tdandroidsaumiersophie.detail.DetailActivity
import com.example.tdandroidsaumiersophie.network.HomeActivity
import com.example.tdandroidsaumiersophie.network.Neworkconst
import com.example.tdandroidsaumiersophie.network.RegisterResult
import com.example.tdandroidsaumiersophie.registre.RegisterActivity
import com.example.tdandroidsaumiersophie.utils.Loader
import com.google.gson.GsonBuilder
import org.json.JSONObject

lateinit var binding: ActivityBasketBinding
private lateinit var basket: Basket



class BasketActivity : AppCompatActivity()/*, BasketCellInterface */{
    lateinit var binding: ActivityBasketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reloadData(Basket.getBasket(this))

        binding.orderButton.setOnClickListener{
            val intent= Intent(this,RegisterActivity::class.java)
            startActivityForResult(intent,RegisterActivity.REQUEST_CODE)
        }

    }

    private fun reloadData(basket: Basket) {

        binding.basketRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.basketRecyclerView.adapter = BasketAdapter(basket, this) {
            basket.items.remove(it)
            basket.save(this)

            reloadData(basket)
        }
    }

    /*override fun onDeleteItem(item: BasketItem) {
        basket.items.remove(item)
        basket.save(this)
    }

    override fun onShowDetail(item: BasketItem) {

    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RegisterActivity.REQUEST_CODE) {
            val sharedPreferences = getSharedPreferences(RegisterActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
            val idUser = sharedPreferences.getInt(RegisterActivity.ID_USER, -1)
            if(idUser != -1) {
                sendOrder(idUser)
            }
        }
    }

    private fun sendOrder(idUser: Int) {
        val message = basket.items.map { "${it.count}x ${it.dish.name}" }.joinToString("\n")

        val queue = Volley.newRequestQueue(this)
        val url = Neworkconst.BASE_URL + Neworkconst.PATH_LOGIN
        val loader = Loader()
        loader.show(this,"récupération enu")

        val jsonData = JSONObject()
        jsonData.put(Neworkconst.ID_SHOP, "1")
        jsonData.put(Neworkconst.ID_USER, idUser)
        jsonData.put(Neworkconst.MSG, message)

        var request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            { response ->
                val builder= AlertDialog.Builder(this)
                builder.setMessage("Votre commande a été prise en compte")
                builder.setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int->
                    basket.clear()
                    basket.save(this)
                    val intent=Intent(this, HomeActivity::class.java)
                    intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                }
                loader.hide(this)
                builder.show()

            },
            { error ->
                loader.hide(this)
                error.message?.let {
                    Log.d("request", it)
                } ?: run {
                    Log.d("request", error.toString())
                    Log.d("request", String(error.networkResponse.data))
                }
            }
        )
        queue.add(request)
    }

    /*
    override fun onDeleteItem(item: BasketItem) {
        val basket = Basket.getBasket(this)
        val itemToDelete = basket.items.firstOrNull { it.dish.name == item.dish.name }
        basket.items.remove(itemToDelete)
        basket.save(this)
        reloadData(basket)
    }
    override fun onShowDetail(item: BasketItem) {
        TODO("Not yet implemented")
    }
     */
}