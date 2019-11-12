package com.timertask

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

import androidx.lifecycle.ViewModelProviders


class MainActivity : AppCompatActivity() {

    private var textView: TextView? = null
    private var timerTask: TimerTaskUtil? = null
    private var context: Context? = null
    private lateinit var model: TimerTaskViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        model = ViewModelProviders.of(this).get(TimerTaskViewModel::class.java)
        getSupportActionBar()?.setTitle("Android Timer");
        textView = findViewById(R.id.timer) as TextView


        val timerObserver = Observer<Time> { time ->

            time.timeToSave?.let { timerTask?.setLong("time", it) }
            timer.text = time.timeToShow

        }
        model.elapsedTime.observe(this, timerObserver)


    }

    override fun onResume() {
        super.onResume()
        model.onResume()

    }

    override fun onPause() {
        super.onPause()
        model.onPause()


    }


}
