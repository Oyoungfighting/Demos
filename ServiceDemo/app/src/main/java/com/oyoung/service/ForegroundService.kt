package com.oyoung.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ForegroundService : LifecycleService() {
    private var value = 0
    var number = MutableLiveData(0)
    override fun onCreate() {
        super.onCreate()
        lifecycleScope.launch {
            while (true) {
                delay(1_000)
//                Log.e("hello", "onCreate: ${value++}")
                number.value = number.value?.plus(1)
            }
        }
        createChannel()
        val intent = Intent(this, ThirdActivity::class.java)
        // 创建通知跳转intent
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        // 创建通知
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setContentTitle("This is a notification.")
            .setContentText("This is the content.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent).build()
        // 开启前台服务
        startForeground(1, builder)
    }

    /**
     * 给activity提供service
     */
    inner class MyBinder :Binder() {
        val getService = this@ForegroundService
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return MyBinder()
    }

    /**
     * 创建消息通道
     */
    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel name first"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            // 注册通道
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "my notification channel id"
    }
}