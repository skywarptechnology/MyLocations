package com.sky.mylocations

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
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

    val onClick: (LocationDetailData) -> Unit = { data ->
        val intent = Intent(this@PlacesActivity, MapsActivity::class.java)
        intent.putExtra(MapsActivity.KEY_TITLE, data.name)
        intent.putExtra(MapsActivity.KEY_PLACEID, data.place_id)
        intent.putExtra(MapsActivity.KEY_LAT, data.geometry.location.lat)
        intent.putExtra(MapsActivity.KEY_LNG, data.geometry.location.lng)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places)
        API_KEY = getString(R.string.google_maps_key)

        setSupportActionBar(ui_act_places_toolBarV)

        val placeDetails = locationApi.getPlaces(
            radius = "1000",
            location = "19,-99",
            types = "food"
        )

        placeDetails.enqueue(object : Callback<PlacesData> {
            override fun onFailure(call: Call<PlacesData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<PlacesData>, response: Response<PlacesData>) {
                val data = response.body()
                data?.results?.let {
                    setupList(it)
                }
            }
        })
    }

    fun setupList(list: List<LocationDetailData>) {

        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)

        ui_act_places_ListV.addItemDecoration(dividerItemDecoration)
        ui_act_places_ListV.layoutManager = layoutManager

        val adapter = PlacesListAdapter(list, onClick)
        setAdapter(adapter)
    }

    private fun setAdapter(adapter: PlacesListAdapter) {
        ui_act_places_ListV.adapter = adapter
    }
}