package com.example.a7minutesworkout

import android.os.CountDownTimer
import androidx.lifecycle.LiveData

class CountDownTimerLiveData(
    private val millisInFuture: Long,
    private val countDownInterval: Long
) : LiveData<Long>() {
    private var countDownTimer: CountDownTimer? = null
    private var remainingDuration: Long = millisInFuture

    init {
        initCountDownTimer()
    }

    fun start() {
        countDownTimer?.start()
        postValue(millisInFuture)
    }

    fun stop() {
        countDownTimer?.cancel()
    }

    fun reset() {
        remainingDuration = millisInFuture
        stop()
        postValue(millisInFuture)
    }

    fun isDone(): Boolean {
        return remainingDuration == millisInFuture
    }

    private fun initCountDownTimer() {
        countDownTimer = object : CountDownTimer(remainingDuration * 1000, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                remainingDuration = millisUntilFinished
                postValue(millisUntilFinished)
            }

            override fun onFinish() {
                postValue(0)
            }
        }
    }

    override fun onInactive() {
        countDownTimer?.cancel()
    }
}