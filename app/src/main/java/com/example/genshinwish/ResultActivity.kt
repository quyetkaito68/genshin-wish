package com.example.genshinwish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.genshinwish.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

//        binding.layoutResult.setOnClickListener{
//            val intent =  Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }

    }
}