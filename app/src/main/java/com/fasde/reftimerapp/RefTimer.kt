package com.fasde.reftimerapp

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RefTimer : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ref_timer_view)

        // Set all the Elements form the View
        val mainTime : TextView = findViewById(R.id.mainTime)
        val overTime : TextView = findViewById(R.id.overTime)
        val startButton : FloatingActionButton = findViewById(R.id.startButton)
        val secondHalfButton : FloatingActionButton = findViewById(R.id.secondHalfButton)
        val gameRevertButton : FloatingActionButton = findViewById(R.id.gameRevertButton)
        var mainTimer = Time()
        var overTimer = Time()
        var firstHalf = GlobalScope.launch {  }
        var secondHalf = GlobalScope.launch {  }
        // Enables Always-on
        setAmbientEnabled()

        startButton.setOnClickListener{
            // start the timer
            firstHalf.cancel()
            firstHalf = GlobalScope.launch {
                while(true){
                    mainTimer.addOneSec()
                    mainTime.text = mainTimer.toString()
                    if(mainTimer.minutes >= 45){
                        break
                    }
                }
                while (true){
                    overTimer.addOneSec()
                    overTime.text = overTimer.toString()
                }
            }
        }
        secondHalfButton.setOnClickListener {
            firstHalf.cancel()
            secondHalf.cancel()
            mainTime.text = mainTimer.secondHalf().toString()
            overTime.text = overTimer.reset().toString()
            secondHalf = GlobalScope.launch {
                while(true){
                    mainTimer.addOneSec()
                    mainTime.text = mainTimer.toString()
                    if(mainTimer.minutes >= 90){
                        break
                    }
                }
                while(true){
                    overTimer.addOneSec()
                    overTime.text.toString()
                }
            }
        }

        gameRevertButton.setOnClickListener {
            firstHalf.cancel()
            secondHalf.cancel()
            mainTime.text = mainTimer.reset().toString()
            overTime.text = overTimer.reset().toString()
            // Stop the ticking Timer
        }


    }
}
