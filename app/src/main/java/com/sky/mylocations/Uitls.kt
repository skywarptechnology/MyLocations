package com.sky.mylocations

import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by skyalligator on 1/20/19.
 * 1:42 PM
 */
val TAG = "LocationApp"
var API_KEY = ""
var BASE_URL = "https://maps.googleapis.com/maps/api/place/"

fun log(txt: String) {
    Log.d(TAG, txt)
}

fun Any?.lg() {
    Log.d(TAG, this?.toString() ?: " null object :P")
}

val retrofitIns: Retrofit by lazy {

    Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        addConverterFactory(GsonConverterFactory.create())
    }.build()
}

val locationApi: LocationApi by lazy {
    retrofitIns.create(LocationApi::class.java)
}


fun GoogleMap.setupLocationOnMap(lat: Double, lng: Double, title: String) {
    val sydney = LatLng(lat, lng)
    addMarker(MarkerOptions().position(sydney).title(title))
    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(sydney, 21.0f)
    moveCamera(cameraUpdate)
}