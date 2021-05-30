package com.example.genshinwish

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.genshinwish.notification.Mp3Service

class MyReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val actionMusic = intent?.getIntExtra("action_music",0)
        val intentService = Intent (context,Mp3Service::class.java)
        intentService.putExtra("action_music_service",actionMusic)
        context?.startService(intentService)

    }
}