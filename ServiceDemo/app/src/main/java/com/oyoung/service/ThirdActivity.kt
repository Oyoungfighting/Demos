package com.oyoung.service

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import androidx.lifecycle.Observer
import com.oyoung.service.databinding.ActivityThirdBinding

/**
 * demo:学习前台服务
 */
class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val serviceConnection = object :ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                (service as ForegroundService.MyBinder).getService.number.observe(this@ThirdActivity, Observer {
                    binding.textView.text = "$it"
                })
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                TODO("Not yet implemented")
            }
        }
        Intent(this, ForegroundService::class.java).apply {
            startService(this)
            bindService(this, serviceConnection, BIND_AUTO_CREATE)
        }
    }
}