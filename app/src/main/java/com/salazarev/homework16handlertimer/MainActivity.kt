package com.salazarev.homework16handlertimer

import Timer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TIMER = "TIMER"
    }

    private lateinit var timerTv: TextView
    private lateinit var startBtn: Button

    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timerTv = findViewById(R.id.tv_timer)
        startBtn = findViewById(R.id.btn_start)

        val startTime = 10

        timer = if (savedInstanceState != null) {
            Timer(WeakReference(timerTv), savedInstanceState.getInt(TIMER),startTime)
        } else {
            Timer(WeakReference(timerTv), startTime)
        }

        startBtn.setOnClickListener {
            timer.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.stop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TIMER, timer.currentTime)
    }
}