package com.example.genshinwish.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.genshinwish.R
import com.example.genshinwish.databinding.ActivitySecondBinding
import com.example.genshinwish.models.Song
import com.example.genshinwish.notification.Mp3Service

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)
        binding.btnStartService.setOnClickListener{
            clickStartService()
        }
        binding.btnStopService.setOnClickListener{
            clickStopService()
        }

    }

    private fun clickStopService() {
        val intent = Intent(this, Mp3Service::class.java)
        stopService(intent)
    }

    private fun clickStartService() {
        val intent = Intent(this, Mp3Service::class.java)
        val bundle = Bundle()
        val song = Song("Genshin Impact Battle Song","Paimon",
            R.drawable.razor,
            R.raw.battle_paimon)
        bundle.putSerializable("object_song",song)
        intent.putExtra("Bundle",bundle)
        startService(intent)
    }

}