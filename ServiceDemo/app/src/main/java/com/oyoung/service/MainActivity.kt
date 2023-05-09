package com.oyoung.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.Observer
import com.oyoung.service.databinding.ActivityMainBinding

/**
 * demo:学习后台服务与绑定服务
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onStart() {
        Log.e("hello", "onStart: activity")
        super.onStart()
        binding.btnStartActivity.setOnClickListener {
            Intent(this, SecondActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.btnStartService.setOnClickListener {
            Intent(this, MyService::class.java).also {
                startService(it)
            }
        }
        binding.btnBindService.setOnClickListener {
            val bindIntent = Intent(this, MyService::class.java)
            val serviceConnection = object :ServiceConnection {
                // service是让我们访问Service的引用
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    (service as MyService.MyBinder).service.numberLiveData.observe(this@MainActivity, Observer {
                        binding.tvNumber.text = "$it"
                    })
                }
                override fun onServiceDisconnected(name: ComponentName?) {}

            }
            startService(bindIntent)
            bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE)
        }

        binding.btnBindAndStart.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            val serviceConnection = object :ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    (service as MyService.MyBinder).service.numberLiveData.observe(this@MainActivity
                    ) {
                        binding.tvNumber.text = "$it"
                    }
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    TODO("Not yet implemented")
                }
            }
            startService(intent)
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        Log.e("hello", "onDestroy: activity")
        super.onDestroy()
    }

    override fun onStop() {
        Log.e("hello", "onStop: activity")
        super.onStop()
       /* Intent(this, MyService::class.java).also {
            stopService(it)
        }*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("hello", "onCreate: activity")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}