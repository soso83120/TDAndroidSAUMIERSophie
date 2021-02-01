package com.example.tdandroidsaumiersophie.network

import android.content.Context
import android.util.Log
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.tdandroidsaumiersophie.R
import com.example.tdandroidsaumiersophie.detail.DetailActivity

open class BaseActivity: AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val menuView= menu?.findItem(R.id.basket)?.actionView
        val countText= menuView?.findViewById(R.id.basketCount) as? TextView
        val count=getItemCount()
        countText?.isVisible=count > 0
        countText?.text=count.toString()

        menuView?.setOnClickListener{
            //Start basket activity
        }
        return true
    }

    private fun getItemCount():Int{
        val sharedPreferences=getSharedPreferences(DetailActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(DetailActivity.ITEM_COUNT,0)

    }
}
