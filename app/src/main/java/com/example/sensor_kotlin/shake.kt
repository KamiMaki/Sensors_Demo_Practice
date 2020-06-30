package com.example.sensor_kotlin

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.TextView
import kotlin.math.pow
import kotlin.math.sqrt


//TODO:宣告SensorManager及Sensor
//TODO:宣告相關全域變數
private lateinit var Speed:TextView
private lateinit var State:TextView
class shake : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shake)
        Speed = findViewById(R.id.textView9)
        State = findViewById(R.id.textView10)
        //TODO:初始化SensorManager、取得加速度感測器、註冊事件監聽器(SensorEventListener)
    }
    private val myAccelerometerListener = object: SensorEventListener {
        //sensor準確度發生變化的時候
        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
        //sensor數值發生變化的時候
        override fun onSensorChanged(event: SensorEvent?) {
            if(event != null){
                //TODO:計算時間差，如果小於指定的時間長度則跳出函式


                //TODO:取得目前加速度


                //TODO:甩動偏移速度 = xyz體感(Sensor)偏移 - 上次xyz體感(Sensor)偏移

                //TODO:保存現在加速度及時間

                //TODO:用公式計算平均速度

                //TODO:如果速度大於閥值，更改TextView中的內容(speed為平均速度及state為"搖動中!!!")並震動，否則將狀態設為"停止搖動..."

            }
        }
    }
    //TODO:onPause()裡面取消註冊Listener

}