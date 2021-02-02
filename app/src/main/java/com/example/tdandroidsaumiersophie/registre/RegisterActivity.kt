package com.example.tdandroidsaumiersophie.registre

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.tdandroidsaumiersophie.Category.CategoryActivity.Companion.USER_PREFERENCES_NAME
import com.example.tdandroidsaumiersophie.R
import com.example.tdandroidsaumiersophie.basket.BasketActivity
import com.example.tdandroidsaumiersophie.databinding.ActivityRegisterBinding
import com.example.tdandroidsaumiersophie.network.NetworkConstant
import com.example.tdandroidsaumiersophie.network.Neworkconst
import com.example.tdandroidsaumiersophie.network.RegisterResult
import com.example.tdandroidsaumiersophie.network.User
import com.google.gson.GsonBuilder
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registered.setOnClickListener {
            if(verifyInformation()){
                launchRequest()
            }else {
                //TODO
                Toast.makeText(this, "Veuillez remplir les champs", Toast.LENGTH_LONG).show();
            }
        }
        binding.buttonLogin.setOnClickListener{
            val intetn = Intent(this, BasketActivity::class.java)
            startActivityForResult(intent,REQUEST_CODE)
        }
    }
    fun launchRequest() {
        setResult(Activity.RESULT_OK)
        finish()
        val queue= Volley.newRequestQueue(this)
        val url= Neworkconst.BASE_URL+Neworkconst.PATH_REGISTER

        val jsonData= JSONObject()
        jsonData.put(Neworkconst.ID_SHOP,"1")
        jsonData.put(Neworkconst.EMAIL,binding.email)
        jsonData.put(Neworkconst.PASSWORD,binding.password)
        jsonData.put(Neworkconst.FIRSTNAME,binding.fname)
        jsonData.put(Neworkconst.LASTNAME,binding.name)

        val request= JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            {response ->
                val userResult=GsonBuilder().create().fromJson(response.toString(), RegisterResult::class.java)
                saveUser(userResult.data)
            },
            {error ->
                error.message?.let{
                    Log.d("request",it)
                }?:run {
                    Log.d("request",error.toString())
                    Log.d("request",String(error.networkResponse.data))
                }

            }
        )
        queue.add(request)

    }
    fun verifyInformation(): Boolean{
        return (binding.email.text?.isNotEmpty() == true &&
                binding.fname.text?.isNotEmpty() == true &&
                binding.name.text?.isNotEmpty() == true &&
                binding.password.text?.count() ?: 0 >= 6)

    }
    fun saveUser(user: User){
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ID_USER, user.id)
        editor.apply()

        setResult(Activity.RESULT_FIRST_USER)
        finish()
    }
    companion object{
        const val REQUEST_CODE = 111
        const val ID_USER = "ID_USER"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }
}