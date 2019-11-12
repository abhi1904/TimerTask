package com.timertask

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.Timer


class TimerTaskViewModel(application: Application) : AndroidViewModel(application) {

    private val mElapsedTime = MutableLiveData<Time>()

    private var mInitialTime: Long = 0
    private val timer: Timer
    private var timerTask: TimerTaskUtil? = null
    private var mainHandler: Handler
    private var timeObject: Time


    val elapsedTime: LiveData<Time>
        get() = mElapsedTime

    init {

        val context = getApplication<Application>().applicationContext

        timerTask = TimerTaskUtil.getInstance(context)
        mainHandler = Handler(Looper.getMainLooper())


        mInitialTime = timerTask?.getLong("time", 0)!!
        timer = Timer()
        timeObject = Time()


    }

    override fun onCleared() {
        super.onCleared()
        mainHandler.removeCallbacks(updateTextTask)
    }

    fun onResume() {
        mainHandler.post(updateTextTask)

    }

    fun onPause() {
        mainHandler.removeCallbacks(updateTextTask)

    }

    companion object {
        private val ONE_SECOND = 1000
    }

    val updateTextTask = object : Runnable {
        override fun run() {
            mInitialTime += 1


            timerTask?.setLong("time", mInitialTime)

            timeObject.timeToSave = mInitialTime
            timeObject.timeToShow = timerTask!!.timerTaskFormat(mInitialTime)

            mElapsedTime.postValue(timeObject)
            mainHandler.postDelayed(this, ONE_SECOND.toLong())


        }
    }

}
