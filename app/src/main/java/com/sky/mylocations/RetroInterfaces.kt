package com.sky.mylocations

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by skyalligator on 1/20/19.
 * 1:32 PM
 */

interface LocationApi {

    @GET("nearbysearch/json")
    fun getPlaces(
        @Query("key") apiKey: String=API_KEY,
        @Query("radius") radius: String,
        @Query("types") types: String,
        @Query("location") location: String
    ): Call<PlacesData>

//    @GET("photo")
//    fun getPhotoData(
//        @Query("key") apiKey: String,
//        @Query("photoreference") photoReference: String,
//        @Query("maxwidth") width: String
//    ): String

    @GET("details/json")
    fun getPlaceDetails(
        @Query("key") apiKey: String,
        @Query("placeid") placeId: String
    ): Call<LocationData>
}