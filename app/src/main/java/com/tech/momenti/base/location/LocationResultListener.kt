package com.tech.momenti.base.location

import android.location.Location

interface LocationResultListener {
    fun getLocation(location: Location)
}