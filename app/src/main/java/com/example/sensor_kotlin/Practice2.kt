package com.example.sensor_kotlin
//練習在同個activity中接多個sensor
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
//TODO:宣告SensorManager及3種Sensor
private lateinit var sm: SensorManager
private lateinit var mLight: Sensor
private lateinit var mTemperature: Sensor
private lateinit var mHumid: Sensor
lateinit var L: TextView
lateinit var T: TextView
lateinit var H: TextView

class Practice2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice2)
        //連接三個TextView
        L = findViewById(R.id.L)//顯示光照度數值
        T = findViewById(R.id.T)//顯示溫度數值
        H = findViewById(R.id.H)//顯示濕度數值
        //TODO:初始化SensorManager

        //TODO:判斷裝置是否有該sensor，避免傳入null到registerListener造成閃退，如果有該sensor則註冊SensorEventListener

    }
    //TODO:設定SensorEventListener(sensor值改變時要做的事)，記得判斷Sensor.type去更改對應的textview
    //TODO:onPause()裡面取消註冊Listener

}