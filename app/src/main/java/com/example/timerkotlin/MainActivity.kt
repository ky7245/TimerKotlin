package com.example.timerkotlin

import android.content.Context
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.os.VibrationEffect.DEFAULT_AMPLITUDE
import android.view.View
import android.widget.TextView
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity() {
    // constructor()
    var timer: Timer? = null
    var timeCount: Int = 0
    var vibrator: Vibrator? = null
    var timerText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        timerText = findViewById(R.id.timeText)
    }

    override fun onStop() {
        super.onStop()
        this.timerStop()
    }



    fun vibrateOneShot(){
        val vibrationEffect = VibrationEffect.createOneShot(100, DEFAULT_AMPLITUDE)
        vibrator?.vibrate(vibrationEffect)
    }

    fun vibrateLongShot() {
        val vibrationEffect = VibrationEffect.createOneShot(1000, DEFAULT_AMPLITUDE)
        vibrator?.vibrate((vibrationEffect))
    }

    fun timerStart(view: View) {
        timeCount = 0
        // timer callback
        val timerCallback: TimerTask.() -> Unit = fun TimerTask.() {

            timeCount += 1
            val handler = Handler(Looper.getMainLooper())
            handler.post(Runnable {
                // メインスレッド上で実行させたい内容
                // UIの変更はメインスレッドでやらないといけない
                timerText?.setText(timeCount.toString())
            })

            when (timeCount%30){
                17, 18, 19 -> { vibrateOneShot() }
                20 -> { vibrateLongShot() }
                28, 29 -> {vibrateOneShot() }
                0 -> {vibrateLongShot() }
            }

        }
        // timer ivent
        if (timer == null)
            timer = Timer()
        timer?.schedule(0, 1000, timerCallback)
    }
    fun timerStop(view: View? = null) {
        timer?.cancel()
        timer = null
    }
}