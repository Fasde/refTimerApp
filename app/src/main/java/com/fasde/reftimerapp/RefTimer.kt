package com.fasde.reftimerapp

import android.os.Bundle
import android.os.SystemClock
import android.support.wearable.activity.WearableActivity
import android.view.View
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RefTimer : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ref_timer_view)

        // Set all the Elements form the View
        val mainTime : TextView = findViewById(R.id.mainTime)
        val overTime : TextView = findViewById(R.id.overTime)
        val pauseStartButton : FloatingActionButton = findViewById(R.id.pauseStartButton)
        val secondHalfButton : FloatingActionButton = findViewById(R.id.secondHalfButton)
        val gameRevertButton : FloatingActionButton = findViewById(R.id.gameRevertButton)
        var ticking = false
        var mainTimer : Time
        var overTimer : Time
        // Enables Always-on
        setAmbientEnabled()

        mainTime.text = getString(R.string.startTime)
        overTime.text = getString(R.string.startTime)

        pauseStartButton.setOnClickListener{
            if(ticking){
                // stop the timer
            } else {
                // start the Timer
            }
            ticking = !ticking
        }
        secondHalfButton.setOnClickListener {
            mainTime.text = getString(R.string.halfTime)
            overTime.text = getString(R.string.startTime)
            // restart the Timer
        }

        gameRevertButton.setOnClickListener {
            mainTime.text = getString(R.string.startTime)
            overTime.text = getString(R.string.startTime)
            // Stop the ticking Timer
        }

        GlobalScope.launch {
            val startMillis = SystemClock.currentThreadTimeMillis()
        }
    }
}
