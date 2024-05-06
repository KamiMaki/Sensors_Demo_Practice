此專案為練習檔，完整版專案連結：https://github.com/KamiMaki/Sensors_Demo_Complete

:bulb: **本節課程你會學到:**
* sensor的基本介紹與控制語法
* 列出本裝置支援的sensor清單
* 三軸加速度感測器的應用
* 一次讀取多種sensor的數值
* 讓手機震動
* 設定倒數計時的timer
* 顯示及隱藏元件
* 根據裝置預設語系顯示對應內容(多國語言)
* **認識HackMD**


---
## 練習程式碼下載:
> [!NOTE]
> 本次課程提供完整專案檔供學員練習，且layout部分皆已實作完畢，學員只需專心理解並實作kotlin程式部分即可，練習檔中會以`TODO:`說明需要實作的內容


:point_right: [練習檔](https://github.com/KamiMaki/Sensors_Demo_Practice)
:100: [完整解答](https://github.com/KamiMaki/Sensors_Demo_Complete)
**下載方式:**
![](https://i.imgur.com/bOVTpNg.png)
(載好後先打開等他build一下)
**主畫面:**
![](https://i.imgur.com/Wn7qcb9.png)
## Sensor基本介紹
:link: [官方說明文件](https://developer.android.com/guide/topics/sensors/sensors_overview)
 

大多數的Android裝置都有內建sensor，主要可以分為三大類:
* Motion Sensors:與加速度及旋轉有關的sensors 
    * 加速度
    * 陀螺儀
    * 重力
* Position Sensors:測量裝置的物理位置
    * 磁場
    * 方位
* Environment Sensors:偵測多種環境參數
    * 溫度
    * 氣壓
    * 照度(光)
    * 濕度
    
也有些穿戴式裝置會有心律感測器
> [!WARNING]  
> **本次課程重點:** 根據以往經驗比較實用的sensor為加速度，用來實作搖一搖功能，且後續的IOT教學也包含了多種sensor的應用。因此接下來的內容主要focus在學會如何讀取內建sensor的數值，實際應用到的sensor則多以加速度sensor為主

## 模擬器之虛擬sensor操作說明
1. 點開模擬器右下角的更多選項
![](https://i.imgur.com/5xYWH9O.png)
2. 切換到virtual sensor標籤即可使用虛擬sensor
![](https://i.imgur.com/xNfrda1.png)

## Sensor架構
* **SensorManager**
用來取得sensor、取得sensor清單、註冊及註銷事件監聽器
**宣告語法:**
```kotlin
var sm: SensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
```
* **Sensor**
用來表達一個sensor，可以用的方法有查看sensor數值的值域、功率、型態等
**宣告語法:**
```kotlin
var mAccelerometer: Sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
```
* **SensorEvent**
包含sensor讀取到的數值、該sensor的種類、sensor的精準度、時間戳記等
* **SensorEventListener**
用來監聽SensorEvent
**宣告語法:**
```kotlin
private val myAccelerometerListener = object: SensorEventListener {
    //sensor準確度發生變化的時候
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
    //sensor數值發生變化的時候
    override fun onSensorChanged(event: SensorEvent?) {
        if(event != null){
            val xValue = event.values[0] // 加速度 - X 軸方向
            val yValue = event.values[1] // 加速度 - Y 軸方向
            val zValue = event.values[2] // 加速度 - Z 軸方向
        }
    }
}
```
> [!TIP]
> 包含兩個function`onAccuracyChanged`及`onSensorChanged`，分別代表精準度及數值發生變化時。當sensor數值有變化時`SensorEventListener `就會監聽到`SensorEvent`，開發者在`onSensorChanged`取得`SensorEvent`中的數值，並實作其他要做的事

**以上都宣告完畢後須透過SensorManager將監聽事件註冊給sensor:**

```kotlin
sm.registerListener(myAccelerometerListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
```

> [!NOTE] 
> 三個參數為(參數1:sensorEventListener,參數2:指定的sensor,參數3:設定事件發生後傳送數值的頻率)
參數3有以下幾種類型:
**SENSOR_DELAY_NORMAL**:200,000 microseconds = 0.2s
**SENSOR_DELAY_UI**: 60,000 microsecond = 0.06s
**SENSOR_DELAY_GAME**: 20,000 microsecond = 0.02s
**SENSOR_DELAY_FASTEST**: 0 microsecond = 0s

**暫停Activity時取消註冊，避免浪費資源**
在本Activity的onPause()裡面取消註冊
```kotlin
override fun onPause() {
    sm.unregisterListener(myAccelerometerListener)
    super.onPause()
}
```

### 宣告流程圖
![](https://i.imgur.com/R8hxNsw.png)

## Demo1:取得裝置的sensor list(不需實作)
layout部分以scroll view搭配textView顯示sensor清單
![](https://i.imgur.com/2gbTS4U.png)

**onCreate中:**
```kotlin
//宣告SensorManager
val sm = getSystemService(SENSOR_SERVICE) as SensorManager
//連接TextView
var TV: TextView = findViewById(R.id.textView)
//取得所有的sensor
val allSensors = sm.getSensorList(Sensor.TYPE_ALL)
//依序append到textView中
for(sensor in allSensors){
    TV.append("${sensor.name}\n")
}
```
> [!TIP]
> 取得的sensor也包含廠商名稱及版本，語法分別為`sensor.vendor`、`sensor.version`，此demo僅顯示sensor名稱

**虛擬機實際畫面:**
![](https://i.imgur.com/0iBLJql.png)

## Practice 1:加速計-顯示三軸加速度數值
### 三軸加速度sensor說明:
![](https://i.imgur.com/yt0cnfX.png)
* 螢幕左下角頂點為原點（x=0, y=0, z=0）
* X 軸為左向右的水平方向，向右X 值增加，向左X 值減少，數值在event.values[0]中
* Y 軸為下向上的垂直方向，向上Y 值增加，向下Y 值減少，數值在event.values[1]中
* Z 軸為後向前的遠近方向，向前Z 值增加，向後Z 值減少，數值在event.values[2]中
* 受到地心引力影響，持續受到g = 9.81向下的加速度
* 有正負號

**完成畫面:**
![](https://i.imgur.com/LzqcxPv.png)
**實作步驟:**
1. 在**onCreate外**宣告全域的`SensorManager`及`Sensor`型態變數，因為onPause時需要取消註冊
```kotlin
private lateinit var sm: SensorManager
private lateinit var mAccelerometer: Sensor
```
> [!TIP]
> `lateinit`讓變數可以晚點初始化，就不用加?判斷變數是否為null

2. 在**onCreate中**初始化sm及mAccelerometer變數(語法看上面)
3. 在**onCreate外**宣告`myAccelerometerListener`變數用來監聽sensor event，透過event.values[]取值，並更改textView的文字內容(可直接更改textView的text屬性(TV.text = "")，記得用.toString()將數值轉成字串)
4. 在**onCreate中**註冊Listener(語法看上面)，可自行選擇DELAY
5. 建立onPause()並在**onPause中**取消註冊
## Practice 2:多重sensor - 在同個activity中接光、溫度、濕度的sensor
**sensor type參考表**
![](https://i.imgur.com/1sRbNUX.png)
**完成畫面:**
![](https://i.imgur.com/guIipjm.png)

**實作步驟:**
1. 大致上和Practice 1差不多，宣告改成三個sensor
2. **在onCreate中**先判斷裝置是否有該sensor，如果有才註冊Listener，不然會出錯
```kotlin
if(sm.getDefaultSensor(Sensor.TYPE_LIGHT)!=null){
    mLight = sm.getDefaultSensor(Sensor.TYPE_LIGHT)
    sm.registerListener(mySensorListener, mLight, SensorManager.SENSOR_DELAY_FASTEST)
}
```
> [!CAUTION]
> Practice 1是假設大家都有加速度sensor才沒有判斷是否為null，但實際開發上應該多注意這種細節，盡量多寫一些方法判斷例外情形

3.在SensorListener中判斷event來自哪種型態的sensor，並更改對應的textView
```kotlin
if(event.sensor.type == Sensor.TYPE_LIGHT) {
    val Light_Value = event.values[0]
    L.text = Light_Value.toString()
}
```
## Practice 3:搖一搖 - 加入觸發條件，觸發成功時震動
> [!NOTE]  
> 觸發條件設定是為了確保使用者真的有搖動裝置，避免輕微幅度的晃動造成功能觸發，而觸發條件的設定也很多種，大家可以自己試試其他方法

**在Manifest文件中加入震動權限**
```xml=
<uses-permission android:name="android.permission.VIBRATE" />`
```
**設計流程**
> [!NOTE]  
> 開始搖動時給予裝置一個加速度，觸發`SensorEventListener`的`onSensorChanged()`，過程中間給予相同的力，加速度保持不變，停止搖動時減速也會造成加速度改變，再次觸發`onSensorChanged()`，將兩次的時間相減計算時間差，加速度差經過公式計算出一個平均速度，兩者都符合設定條件時即可觸發搖一搖事件，觸發條件的閥值也可以自己更改，試試看什麼組合才是最適合的

![](https://i.imgur.com/klggh5N.png)

**公式**
$DeltaX = X_{new}-X_{old}$
$SPEED = \sqrt{DeltaX^2+DeltaY^2+DeltaZ^2}*10000/IntervalTime$

**虛擬機實際畫面**
| 搖動前| 搖動後 |
| -------- | -------- |
| ![](https://i.imgur.com/Q1slud1.png)|![](https://i.imgur.com/DXff1K8.png)|

**實作步驟:**
1. 宣告需要用到的全域變數

```kotlin
private val SPEED_THRESHOLD = 2000//搖動速度閥值
private val UPDATE_TIME_INTERVAL = 150//達成觸發條件之搖動持續時間
private var mLastX = 0F//一開始的X方向加速度
private var mLastY = 0F//一開始的Y方向加速度
private var mLastZ = 0F//一開始的Z方向加速度
private var mSpeed = 0F//三軸加速度平均之搖動速度
private var mLastUpdateTime:Long = 0//一開始的時間
```
2. **在onSensorChanged中**計算時間差，如果小於指定的時間長度則跳出函式

```kotlin
var mCurrentUpdateTime = System.currentTimeMillis()//取得現在時間
var mTimeInterval = mCurrentUpdateTime- mLastUpdateTime//計算時間差
if (mTimeInterval<UPDATE_TIME_INTERVAL)//如果小於指定的時間長度則跳出函式
    return
```
3. 保存現在加速度及時間
4. 速度平均計算公式
```kotlin
mSpeed = sqrt(DeltaX.pow(2)+DeltaY.pow(2)+DeltaZ.pow(2))* 10000/mTimeInterval
```

5. 如果平均速度>SPEED_THRESHOLD則顯示平均速度及更改狀態，同時震動一下
```kotlin
val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
vibrator.vibrate(100)//參數為震動持續時間
```
## Practice 4:手速大賽 - 全部給我搖起來!!!
**遊戲循環**
![](https://i.imgur.com/Jrtb337.png)
**[CountDownTimer](https://developer.android.com/reference/android/os/CountDownTimer):** 在指定長度時間內以固定頻率執行task，後面接.start()開始執行
```kotlin
//倒數30秒 每一秒執行一次onTick()中的task
//時間結束時執行onFinish()
object : CountDownTimer(30000, 1000) {//(總時間、每次間隔時間)
    override fun onFinish() {}
    override fun onTick(millisUntilFinished: Long) {}
}.start()
```
**顯示與隱藏元件:** 更改visibility屬性達到顯示與隱藏效果
```kotlin
start.visibility = View.VISIBLE//button切換為顯示
restart.visibility = View.INVISIBLE//button切換為隱藏
```

:::success
**開發過程中發現問題:** CountDownTimer為一個Thread，可以想像成在背景獨立運作，有時候在遊玩時會不小心跳出activity，但此時CountDownTimer仍持續運作中，導致再次進入遊戲時會有兩個CountDownTimer同時運作
**解決目標:** 離開activity時要中斷CountDownTimer的運作

**解決方法:** 新增一個flag，在activity onPause()時設為false，CountDownTimer中如果flag為false則cancel()//結束CountDownTimer
:::
