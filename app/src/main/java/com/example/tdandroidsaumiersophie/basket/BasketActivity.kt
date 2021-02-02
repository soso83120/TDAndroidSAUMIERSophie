package com.example.tdandroidsaumiersophie.basket

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tdandroidsaumiersophie.R
import com.example.tdandroidsaumiersophie.databinding.ActivityBasketBinding
import com.example.tdandroidsaumiersophie.detail.DetailActivity
import com.example.tdandroidsaumiersophie.network.Neworkconst
import com.example.tdandroidsaumiersophie.registre.RegisterActivity

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
        //basket.clear()
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