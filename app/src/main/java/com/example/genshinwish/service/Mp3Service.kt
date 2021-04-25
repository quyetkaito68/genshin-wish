package com.example.genshinwish.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.genshinwish.MyApplication.Companion.CHANNEL_ID
import com.example.genshinwish.R
import com.example.genshinwish.SecondActivity
import com.example.genshinwish.model.Song

class Mp3Service: Service() {

    lateinit var mediaPlayer: MediaPlayer

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
            startMusic(song)
        }
        return START_NOT_STICKY
    }

    private fun startMusic(song: Song) {
            mediaPlayer = MediaPlayer.create(applicationContext,song.resource)
            mediaPlayer.start()

    }

    private fun sendNotification(song: Song) {
        val intent = Intent(this, SecondActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent:PendingIntent = PendingIntent.getActivity(this,0,intent,0)

        val bitmap: Bitmap = BitmapFactory.decodeResource(resources,song.image)

        val remoteViews: RemoteViews = RemoteViews(packageName, R.layout.layout_custiom_notification)
        remoteViews.setTextViewText(R.id.tv_songname,song.title)
        remoteViews.setTextViewText(R.id.tv_singer,song.singer)
        remoteViews.setImageViewBitmap(R.id.img_song,bitmap)
        remoteViews.setImageViewResource(R.id.btn_play_or_pause, R.drawable.ic_pause)



        val notify:Notification = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setCustomContentView(remoteViews)
            .setSound(null)
            .build()
        startForeground(1,notify)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("quyetkaito","Service onDestroy")
            mediaPlayer.release()
    }
}