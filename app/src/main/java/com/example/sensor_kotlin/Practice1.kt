package com.example.sensor_kotlin
//讀取三軸加速度sensor的值
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlin.math.abs

//TODO:宣告SensorManager及Sensor
lateinit var X: TextView
lateinit var Y: TextView
lateinit var Z: TextView

class Practice1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice1)
        //連接三個TextView
        X = findViewById(R.id.X)
        Y = findViewById(R.id.Y)
        Z = findViewById(R.id.Z)
        //TODO:初始化SensorManager、取得加速度感測器、註冊事件監聽器(SensorEventListener)

    }
    //TODO:宣告並設定SensorEventListener

    //TODO:override onPause函式，裡面取消註冊事件監聽器(SensorEventListener)

}