package com.example.genshinwish

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.getSystemService

class MyApplication: Application() {
    companion object{
        const val CHANNEL_ID = "my_channel_id_1"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(CHANNEL_ID,"Channel Service Example",NotificationManager.IMPORTANCE_DEFAULT)
            val manager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
            manager.createNotificationChannel(notificationChannel)
        }
    }
}