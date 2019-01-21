package com.sky.mylocations.support

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sky.mylocations.retro.LocationApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.lang.NullPointerException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by skyalligator on 1/20/19.
 * 1:42 PM
 */

val TAG = "LocationApp"
var API_KEY = ""
var BASE_URL = "https://maps.googleapis.com/maps/api/place/"

/**
 * general log for application
 */
fun log(txt: String) {
    Log.d(TAG, txt)
}

/**
 * convenient func for printing object even its null reference
 */
fun Any?.lg() {
    Log.d(TAG, this?.toString() ?: " null object :P")
}

/**
 * retrofit lazy initialization variable
 */
val retrofitIns: Retrofit by lazy {

    Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        addConverterFactory(GsonConverterFactory.create())
    }.build()
}

/**
 * location api by lazy initialization
 */
val locationApi: LocationApi by lazy {
    retrofitIns.create(LocationApi::class.java)
}

/**
 * set location on map with zoom and marker
 */
fun GoogleMap.setupLocationOnMap(lat: Double, lng: Double, title: String) {
    val sydney = LatLng(lat, lng)
    addMarker(MarkerOptions().position(sydney).title(title))
    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(sydney, 16.0f)
    moveCamera(cameraUpdate)
}

fun Activity.shareAddressViaOtherApps(subject: String, message: String) {
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
    sharingIntent.putExtra(Intent.EXTRA_TEXT, message)
    startActivity(Intent.createChooser(sharingIntent, "Share Address"))
}

/**
 * co-routine implementation for retrofir future object
 */
suspend fun <T> Call<T>.callApi(): T? {
    return suspendCoroutine { continuation ->

        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val data = response.body()
                if (data != null)
                    continuation.resume(data)
                else
                    continuation.resumeWithException(NullPointerException("Null data Object"))
            }
        })
    }
}

/**
 * util func for ignoring an exception thrown by supplied inline lambda
 */
inline fun ignoreEx(call: () -> Unit) {
    try {
        call()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * this is support util func for CoroutineScope.launch
 * while ignoreing exception
 *
 * @param fnc provide callback crossinline fnc for coroutine launch function
 */
inline fun CoroutineScope.launchWithoutEx(crossinline fnc: suspend () -> Unit) {
    launch {
        try {
            fnc()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}