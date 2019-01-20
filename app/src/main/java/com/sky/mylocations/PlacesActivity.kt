package com.sky.mylocations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_places.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by skyalligator on 1/19/19.
 * 8:50 PM
 */
class PlacesActivity : AppCompatActivity() {

    var placesDetailsData= emptyList<PlaceDetailsData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places)
        setSupportActionBar(ui_act_places_toolBarV)

        val placeDetails = retrofitIns.create(LocationApi::class.java).getPlaceDetails()
        placeDetails.enqueue(object:Callback<PlacesData>{
            override fun onFailure(call: Call<PlacesData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<PlacesData>, response: Response<PlacesData>) {
                val data = response.body()
                placesDetailsData = data?.results ?: emptyList()
                setupList()
            }

        })

    }


    fun setupList() {
        val adapter = PlacesListAdapter(placesDetailsData)
        ui_act_places_ListV.layoutManager=LinearLayoutManager(this)
        ui_act_places_ListV.adapter=adapter
    }
}