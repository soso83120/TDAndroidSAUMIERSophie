package com.example.tdandroidsaumiersophie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tdandroidsaumiersophie.databinding.ActivityHomeBinding
import android.widget.Toast

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button4.setOnClickListener{
            Toast.makeText(applicationContext, "oui", Toast.LENGTH_LONG).show()
        }

    }
}