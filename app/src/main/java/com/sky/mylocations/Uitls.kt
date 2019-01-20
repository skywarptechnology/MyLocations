package com.sky.mylocations

import android.util.Log

/**
 * Created by skyalligator on 1/20/19.
 * 1:42 PM
 */
val TAG = "LocationApp"

fun log(txt: String) {
    Log.d(TAG, txt)
}

fun Any?.lg() {
    Log.d(TAG, this?.toString() ?: " null object :P")
}