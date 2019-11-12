package com.timertask

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Time {

    @SerializedName("timeToShow")
    @Expose
    var timeToShow: String? = null
    @SerializedName("timeToSave")
    @Expose
    var timeToSave: Long? = null

}
