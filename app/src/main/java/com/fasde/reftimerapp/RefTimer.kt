package com.fasde.reftimerapp

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RefTimer : WearableActivity() {

    private lateinit var mainTime: TextView
    private lateinit var overTime: TextView
    private lateinit var halfTimeIndicator : TextView
    private lateinit var mainTimer: Time
    private lateinit var overTimer: Time
    private lateinit var startButton: FloatingActionButton
    private lateinit var secondHalfButton: FloatingActionButton
    private lateinit var gameRevertButton: FloatingActionButton
    private var isTicking = false
    private var isFirstHalf = true
    private lateinit var firstHalfCoroutine : CoroutineContext
    private lateinit var secondHalfCoroutine : CoroutineContext


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ref_timer_view)
        val ticker = GlobalScope.launch {
            mainTime = findViewById(R.id.mainTime)
            overTime = findViewById(R.id.overTime)
            while (true){
                delay(1000)
                mainTime.text = mainTimer.toString()
                overTime.text = overTimer.toString()
            }
        }

        // Set all the Elements from the View
        startButton = findViewById(R.id.startButton)
        secondHalfButton = findViewById(R.id.secondHalfButton)
        gameRevertButton = findViewById(R.id.gameRevertButton)
        halfTimeIndicator = findViewById(R.id.halfTimeIndicator)
        mainTimer = Time()
        overTimer = Time()
        halfTimeIndicator.text="1.HZ"
        // Enables Always-on
        setAmbientEnabled()


        startButton.setOnClickListener {
            if(!isTicking){
                if(isFirstHalf){
                    firstHalfCoroutine = GlobalScope.launch {
                        while (true){
                            delay(1000)
                            countUpFirstHalf()
                        }
                    }
                    isTicking = true
                } else {
                    secondHalfCoroutine = GlobalScope.launch {
                        while(true){
                            delay(990)
                            countUpSecondHalf()
                        }
                    }
                    isTicking = true
                }
            } else {
                if(isFirstHalf){
                    firstHalfCoroutine.cancel()
                    isTicking = false
                } else {
                    secondHalfCoroutine.cancel()
                    isTicking = false
                }
            }
        }
        secondHalfButton.setOnClickListener {
            firstHalfCoroutine.cancel()
            isTicking = false
            isFirstHalf = false
            mainTimer.secondHalf()
            overTimer.reset()
            halfTimeIndicator.text="2.HZ"
        }

        gameRevertButton.setOnClickListener {
            firstHalfCoroutine.cancel()
            secondHalfCoroutine.cancel()
            isTicking = false
            isFirstHalf = true
            mainTimer.reset()
            overTimer.reset()
            halfTimeIndicator.text="1.HZ"
        }


    }

    private fun countUpFirstHalf() {
        if(mainTimer.minutes == 45){
            overTimer.addOneSec()
        } else {
            mainTimer.addOneSec()
        }
    }

    private fun countUpSecondHalf(){
        if(mainTimer.minutes == 90){
            overTimer.addOneSec()
        } else {
            mainTimer.addOneSec()
        }
    }
}
