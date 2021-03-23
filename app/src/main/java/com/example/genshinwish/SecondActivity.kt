package com.example.genshinwish

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.genshinwish.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)

        var uriPath: String = "android.resource://" + packageName.toString() + "/"
        var uri: Uri = Uri.parse(uriPath + R.raw.five_star_wish)
        binding.videoView.setVideoURI(uri)
        binding.videoView.start()

    }
}