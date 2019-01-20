package com.sky.mylocations

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by skyalligator on 1/20/19.
 * 1:23 PM
 */

val retrofitIns: Retrofit by lazy {

    Retrofit.Builder().apply {
        baseUrl("https://maps.googleapis.com/maps/api/place/")
        addConverterFactory(GsonConverterFactory.create())
    }.build()
}
