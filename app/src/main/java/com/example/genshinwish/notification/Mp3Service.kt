package com.example.genshinwish.notification

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.genshinwish.MyApplication.Companion.CHANNEL_ID
import com.example.genshinwish.MyReceiver
import com.example.genshinwish.R
import com.example.genshinwish.activities.SecondActivity
import com.example.genshinwish.fragments.MusicFragment
import com.example.genshinwish.models.Song
import java.lang.Exception

class Mp3Service : Service() {

    var ACTION_PAUSE = 1
    var ACTION_RESUME = 2
    var ACTION_CLEAR = 3
    var isPlaying: Boolean = false
    lateinit var mSong: Song
    var mediaPlayer: MediaPlayer?=null


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("quyetkaito", "Service onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val bundle: Bundle? = intent?.getBundleExtra("Bundle")
        if (bundle != null) {
            val song = bundle.getSerializable("object_song") as Song
            mSong = song
            //startMusic(song)
            sendNotification(song)
            //sendNotification2()
        }
        //xử lý sự kiện cho notification
        val actionMusic = intent?.getIntExtra("action_music_service", 0)
        if (actionMusic != null) {
            handleActionMusic(actionMusic)
        }
        return START_NOT_STICKY
    }

    private fun sendNotification2() {
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("test")
            .setContentText("ahihi")
            .setSmallIcon(R.drawable.razor)
            .build()
        startForeground(1, notification)
    }

   private fun startMusic(song: Song) {
        // mediaPlayer = MediaPlayer.create(applicationContext, song.resource)
        mediaPlayer = MediaPlayer()
        try {
            mediaPlayer?.isLooping=true
            mediaPlayer?.setDataSource(song.url) //play audio in storage demo
            mediaPlayer?.prepare()
            mediaPlayer?.start()
        } catch (e: Exception) {
        }
        //mediaPlayer.start()
        isPlaying = true

    }

    private fun handleActionMusic(action: Int) {
        when (action) {
            ACTION_PAUSE -> {
                pauseMusic()
            }
            ACTION_RESUME -> {
                resumeMusic()
            }
            ACTION_CLEAR -> {
                stopSelf()
            }
        }
    }

    private fun resumeMusic() {
        if (mediaPlayer != null && !isPlaying) {
            mediaPlayer?.start()
            isPlaying = true
            sendNotification(mSong)  //phải gửi lại notification thì mới update dc view của notification
        }
    }

    private fun pauseMusic() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer?.pause()
            isPlaying = false
            sendNotification(mSong) //phải gửi lại notification thì mới update dc view của notification
        }
    }

    private fun sendNotification(song: Song) {
        val intent = Intent(this, SecondActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, song.image)

        val remoteViews: RemoteViews =
            RemoteViews(packageName, R.layout.layout_custiom_notification)
        remoteViews.setTextViewText(R.id.tv_songname, song.title)
        remoteViews.setTextViewText(R.id.tv_singer, song.singer)
        remoteViews.setImageViewBitmap(R.id.img_song, bitmap)
        remoteViews.setImageViewResource(R.id.btn_play_or_pause, R.drawable.ic_pause)

        //xử lý action click và view notification
        //Log.e("quyetkaito",isPlaying.toString())
        if (isPlaying) {
            remoteViews.setOnClickPendingIntent(R.id.btn_play_or_pause,
                getPendingIntent(this, ACTION_PAUSE))
            remoteViews.setImageViewResource(R.id.btn_play_or_pause, R.drawable.ic_pause)
        } else {
            remoteViews.setOnClickPendingIntent(R.id.btn_play_or_pause,
                getPendingIntent(this, ACTION_RESUME))
            remoteViews.setImageViewResource(R.id.btn_play_or_pause, R.drawable.ic_play)
        }
        remoteViews.setOnClickPendingIntent(R.id.btn_close, getPendingIntent(this, ACTION_CLEAR))
        //end - xử lý action click


        val notify: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.razor)
            .setCustomContentView(remoteViews)
            .setSound(null)
            .build()
        startForeground(1, notify)
    }

    private fun getPendingIntent(context: Context, action: Int): PendingIntent? {
        val intent = Intent(this, MyReceiver::class.java)
        intent.putExtra("action_music", action) //gửi cho Receiver giá trị của Action
        return PendingIntent.getBroadcast(context.applicationContext,
            action,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("quyetkaito", "Service onDestroy")
        mediaPlayer?.release()

    }
}