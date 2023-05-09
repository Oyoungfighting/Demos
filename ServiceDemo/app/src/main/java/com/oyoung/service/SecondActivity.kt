package com.oyoung.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import androidx.lifecycle.Observer
import com.oyoung.service.databinding.ActivitySecondBinding

/**
 * demo:测试多组件与service通信
 */
class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding


    override fun onStart() {
        super.onStart()
        binding.secondBtnBindService.setOnClickListener {
            val bindIntent = Intent(this, MyService::class.java)
            val serviceConnection = object :ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    (service as MyService.MyBinder).service.numberLiveData.observe(this@SecondActivity, Observer {
                        binding.secondTvNumber.text = "$it"
                    })
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    TODO("Not yet implemented")
                }
            }
            bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}