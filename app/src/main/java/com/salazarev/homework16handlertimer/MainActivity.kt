package com.salazarev.homework16handlertimer

import Timer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.lang.ref.WeakReference

private lateinit var timerTv: TextView
private lateinit var startBtn: Button

class MainActivity : AppCompatActivity() {
    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timerTv = findViewById(R.id.tv_timer)
        startBtn = findViewById(R.id.btn_start)
        val startTime = 10

        timer = if (savedInstanceState != null) {
            Timer(WeakReference(timerTv), startTime, savedInstanceState.getInt("time"))
        } else Timer(WeakReference(timerTv), startTime)

        startBtn.setOnClickListener {
            timer.start()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("time", timer.currentTime)
    }
}