package com.oyoung.service

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : LifecycleService() {
    private var value = 0
    val numberLiveData = MutableLiveData(0)
    override fun onCreate() {
        Log.e("hello", "onCreate: service")
        super.onCreate()
    }

    /**
     * startService
     */
    /*override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("hello", "onStartCommand: service")
        lifecycleScope.launch {
            while (true) {
                delay(1000)
                Log.e("hello", "onStartCommand: ${value++}")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }*/

    override fun onDestroy() {
        Log.e("hello", "onDestroy: service")
        super.onDestroy()
    }

    inner class MyBinder : Binder() {
        val service = this@MyService
    }
    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        lifecycleScope.launch{
            while (true) {
                delay(1000)
                numberLiveData.value = numberLiveData.value?.plus(1)
            }
        }
        return MyBinder()
    }
}