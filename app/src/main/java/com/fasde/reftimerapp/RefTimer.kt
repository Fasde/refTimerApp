package com.fasde.reftimerapp

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RefTimer : WearableActivity() {

    lateinit var mainTime: TextView
    lateinit var overTime: TextView
    lateinit var mainTimer: Time
    lateinit var overTimer: Time


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ref_timer_view)

        // Set all the Elements form the View
        mainTime = findViewById(R.id.mainTime)
        overTime = findViewById(R.id.overTime)
        val startButton: FloatingActionButton = findViewById(R.id.startButton)
        val secondHalfButton: FloatingActionButton = findViewById(R.id.secondHalfButton)
        val gameRevertButton: FloatingActionButton = findViewById(R.id.gameRevertButton)
        mainTimer = Time()
        overTimer = Time()
        var firstHalf = GlobalScope.launch { }
        var secondHalf = GlobalScope.launch { }
        var firstHalfRunning = false
        var secondHalfRunning = false
        var halftime1 = true
        var gameEnded = false
        var setToSecondHalf = false
        // Enables Always-on
        setAmbientEnabled()

        GlobalScope.launch {
            while (true) {
                delay(990)
                if (gameEnded) {
                    if (firstHalf.isActive) firstHalf.cancel()
                    if (secondHalf.isActive) secondHalf.cancel()
                } else {
                    if (!halftime1 && !setToSecondHalf) {
                        mainTime.text = mainTimer.secondHalf().toString()
                        overTime.text = overTimer.reset().toString()
                        setToSecondHalf = true
                    }
                    updateTimes()
                }
            }
        }


        startButton.setOnClickListener {
            // start the timer
            gameEnded = false
            if (halftime1) {
                if (!firstHalfRunning) {
                    if (firstHalf.isActive) firstHalf.cancel()
                    firstHalfRunning = true
                    firstHalf = GlobalScope.launch {
                        while (true) {
                            delay(990)
                            launch {
                                tickFirstHalf()
                            }
                        }
                    }
                }
            } else {
                if (!secondHalfRunning) {
                    if (secondHalf.isActive) secondHalf.cancel()
                    secondHalfRunning = true
                    secondHalf = GlobalScope.launch {
                        while (true) {
                            delay(990)
                            launch {
                                tickSecondHalf()
                            }
                        }
                    }
                }

            }
        }
        secondHalfButton.setOnClickListener {
            if (halftime1) {
                secondHalfRunning = true
                firstHalfRunning = false
                if (firstHalf.isActive) firstHalf.cancel()
                halftime1 = false
            }
        }

        gameRevertButton.setOnClickListener {
            gameEnded = true
            // Stop the ticking Timer
        }


    }

    private fun tickFirstHalf() {
        if (mainTimer.minutes < 45) {
            mainTimer.addOneSec()
        } else {
            overTimer.addOneSec()
        }
    }

    private fun tickSecondHalf() {
        if (mainTimer.minutes < 90) {
            mainTimer.addOneSec()
        } else {
            overTimer.addOneSec()
        }
    }

    private fun updateTimes() {
        mainTime.text = mainTimer.toString()
        overTime.text = overTimer.toString()
    }
}
