package com.sky.mylocations.retro

import com.sky.mylocations.support.LocationData
import com.sky.mylocations.support.PlacesData
import com.sky.mylocations.support.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by skyalligator on 1/20/19.
 * 1:32 PM
 */

/**
 * Retrofit implementation for google location service
 */
interface LocationApi {

    @GET("nearbysearch/json")
    fun getPlaces(
        @Query("key") apiKey: String= API_KEY,
        @Query("radius") radius: String,
        @Query("types") types: String,
        @Query("location") location: String
    ): Call<PlacesData>

    @GET("details/json")
    fun getPlaceDetails(
        @Query("key") apiKey: String,
        @Query("placeid") placeId: String
    ): Call<LocationData>
}