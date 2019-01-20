package com.sky.mylocations

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by skyalligator on 1/20/19.
 * 1:32 PM
 */

interface LocationApi{

    @GET("nearbysearch/json?location=19,-99&radius=1000&types=food&key=AIzaSyDYy7YxD2uD5mJbWSGgpasQLte4HrGiepo")
    fun getPlaceDetails(): Call<PlacesData>

}