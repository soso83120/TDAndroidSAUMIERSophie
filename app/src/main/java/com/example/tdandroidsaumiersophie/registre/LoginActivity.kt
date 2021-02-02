package com.example.tdandroidsaumiersophie.registre

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.tdandroidsaumiersophie.R
import com.example.tdandroidsaumiersophie.basket.BasketActivity
import com.example.tdandroidsaumiersophie.databinding.ActivityLoginBinding
import com.example.tdandroidsaumiersophie.databinding.ActivityRegisterBinding
import com.example.tdandroidsaumiersophie.network.Neworkconst
import com.example.tdandroidsaumiersophie.network.RegisterResult
import com.example.tdandroidsaumiersophie.network.User
import com.example.tdandroidsaumiersophie.utils.Loader
import com.google.gson.GsonBuilder
import org.json.JSONObject




class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLoginaccount.setOnClickListener {
            if(verifyInformations()) {
                launchRequest()
                //return to Basket
                val intent = Intent(this, BasketActivity::class.java)
                startActivityForResult(intent, RegisterActivity.REQUEST_CODE)
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun launchRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = Neworkconst.BASE_URL + Neworkconst.PATH_LOGIN
        val loader = Loader()
        loader.show(this,"récupération enu")

        val jsonData = JSONObject()
        jsonData.put(Neworkconst.ID_SHOP, "1")
        jsonData.put(Neworkconst.EMAIL, binding.email)
        jsonData.put(Neworkconst.PASSWORD, binding.password)

        var request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            { response ->
                loader.hide(this)
                val userResult = GsonBuilder().create().fromJson(response.toString(), RegisterResult::class.java)
                saveUser(userResult.data)
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

    fun verifyInformations(): Boolean {
        return (binding.email.text?.isNotEmpty() == true)
    }

    fun saveUser(user: User) {
        val sharedPreferences = getSharedPreferences(RegisterActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(RegisterActivity.ID_USER, user.id)
        editor.apply()

        setResult(Activity.RESULT_FIRST_USER)
        finish()
    }
}