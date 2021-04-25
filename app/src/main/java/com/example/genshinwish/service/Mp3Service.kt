package com.example.genshinwish.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class Mp3Service: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("quyetkaito","Service start")

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("quyetkaito","Service is destroyed")
        Log.e("quyetkaito","test create branch local, push remote")
    }
}