package com.example.sensor_kotlin

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.math.pow
import kotlin.math.sqrt


private lateinit var start : Button
private lateinit var restart : Button
private lateinit var img : ImageView
private lateinit var point : TextView

private lateinit var sm: SensorManager
private lateinit var mAccelerometer: Sensor
private lateinit var Remain_Time_Text :TextView
private lateinit var Remain_Time :TextView
//TODO:宣告相關全域變數(參考搖一搖)
//TODO:新增變數用來儲存分數
class Shaking_contest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shaking_contest)
        sm = getSystemService(SENSOR_SERVICE) as SensorManager
        //透過Sensormanager選取預設的加速度sensor
        mAccelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        //註冊Listener(參數一:sensorEventListener,參數二:指定的sensor,參數三:設定事件發生後傳送數值的頻率)
        sm.registerListener(myAccelerometerListener, mAccelerometer, SensorManager.SENSOR_DELAY_GAME)
        start = findViewById(R.id.button7)//綠色開始按鈕
        restart = findViewById(R.id.button8)//紅色重新開始按鈕
        img = findViewById(R.id.imageView)//手握手機的圖片
        point = findViewById(R.id.textView12)//中間顯示的灰色分數
        Remain_Time_Text = findViewById(R.id.textView13)//"剩餘時間:"這段文字
        Remain_Time = findViewById(R.id.textView14)//剩餘時間秒數
        //TODO:設定start button要做的事(初始化分數、設定layout、建立倒數器(每秒更新一次時間，時間結束時取消註冊Listener避免繼續更新分數))

        //TODO:設定restart button要做的事(重新開始時整理layout至初始狀態)

    }
    private val myAccelerometerListener = object: SensorEventListener {
        //sensor準確度發生變化的時候
        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
        //sensor數值發生變化的時候
        override fun onSensorChanged(event: SensorEvent?) {
            if(event != null){

                //TODO:和搖一搖基本上一樣，達成觸發條件時將全域的得分變數+=1
            }
        }
    }
    override fun onPause() {
        sm.unregisterListener(myAccelerometerListener)
        super.onPause()
    }
}