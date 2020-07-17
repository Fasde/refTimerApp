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

    fun reset(){
        minutes = 0
        seconds = 0
    }

    fun secondHalf(){
        minutes = 45
        seconds = 0
    }
}