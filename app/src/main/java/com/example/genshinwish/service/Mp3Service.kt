package com.example.genshinwish.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.genshinwish.MyApplication.Companion.CHANNEL_ID
import com.example.genshinwish.SecondActivity
import com.example.genshinwish.model.Song

class Mp3Service: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("quyetkaito","Service onCreate")
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val bundle: Bundle? = intent?.getBundleExtra("Bundle")
        if (bundle!=null){
            val song = bundle.getSerializable("object_song") as Song
            Log.e("quyetkaito",song.title)
            sendNotification(song)
        }
        return START_NOT_STICKY
    }

    private fun sendNotification(song: Song) {
        val intent = Intent(this, SecondActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent:PendingIntent = PendingIntent.getActivity(this,0,intent,0)
        val notify = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentText(song.singer)
            .setContentTitle(song.title)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1,notify)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("quyetkaito","Service onDestroy")
    }
}