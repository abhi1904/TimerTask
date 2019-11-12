package com.timertask

import android.content.Context
import android.content.SharedPreferences

open class TimerTaskUtil private constructor(context: Context) {

    private val mSharedPreferences: SharedPreferences
    private val PREF_FILE = "timertask"


    init {
        mSharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        if (sPreferencesManagerInstance != null) {
            throw RuntimeException("Use getInstance() method to get the single instance of this class.")
        }
    }

    //Make singleton from serialize and deserialize operation.
    protected fun readResolve(context: Context): TimerTaskUtil? {
        return getInstance(context)
    }


    /**
     * Set a long shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    fun setLong(key: String, value: Long) {
        val editor = mSharedPreferences.edit()
        editor.putLong(key, value)
        editor.commit()
    }

    /**
     * Get a long shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference
     * isn't found.
     * @return value - long containing value of the shared preference if
     * found.
     */
    fun getLong(key: String, defValue: Long): Long {
        return mSharedPreferences.getLong(key, defValue)
    }


    fun clearAll() {
        mSharedPreferences.edit().clear().commit()
    }

    companion object {
        @Volatile
        private var sPreferencesManagerInstance: TimerTaskUtil? = null

        @Synchronized
        fun getInstance(context: Context): TimerTaskUtil? {
            if (sPreferencesManagerInstance == null) { //if there is no instance available... create new one
                synchronized(TimerTaskUtil::class.java) {
                    if (sPreferencesManagerInstance == null)
                        sPreferencesManagerInstance = TimerTaskUtil(context)
                }
            }
            return sPreferencesManagerInstance
        }
    }

    fun timerTaskFormat(mInitialTime: Long): String {
        var timeformat: String = ""
        val sec = mInitialTime % 60
        val minutes = mInitialTime % 3600 / 60
        val hours = mInitialTime % 86400 / 3600
        val days = mInitialTime / 86400

        if (days > 0) {
            timeformat =
                days.toString() + "d" + " " + hours.toString() + "h" + " " + minutes.toString() + "m" + " " + sec.toString() + "s"
        } else if (hours > 0) {
            timeformat =
                hours.toString() + "h" + " " + minutes.toString() + "m" + " " + sec.toString() + "s"
        } else if (minutes > 0) {
            timeformat = minutes.toString() + "m" + " " + sec.toString() + "s"
        } else {
            timeformat = sec.toString() + "s";
        }
        return timeformat;
    }

}