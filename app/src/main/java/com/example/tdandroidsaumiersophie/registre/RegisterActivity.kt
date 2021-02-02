package com.example.tdandroidsaumiersophie.registre

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tdandroidsaumiersophie.R
import com.example.tdandroidsaumiersophie.databinding.ActivityRegisterBinding

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
            }
        }
    }

    fun launchRequest() {
        setResult(Activity.RESULT_OK)
        finish()

    }
    fun verifyInformation(): Boolean{
        return true
    }

    companion object{
        const val REQUEST_CODE = 111
    }
}