package com.fasde.reftimerapp

class Time(var minutes : Int = 0, var seconds : Int = 0){
    override fun toString(): String {
        return minutes.toString().padStart(2, '0') + ":" + seconds.toString().padStart(2, '0')
    }
    fun addOneSec() {
        if(seconds == 59){
            minutes++
            seconds = 0
        } else {
            seconds++
        }
    }
}