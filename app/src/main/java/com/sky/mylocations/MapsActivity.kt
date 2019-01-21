package com.sky.mylocations

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_maps.*
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        val KEY_LAT = "lat"
        val KEY_LNG = "lng"
        val KEY_TITLE = "title"
        val KEY_PLACEID = "place_id"
    }

    var lat: Double = 0.0
    var lng: Double = 0.0
    lateinit var title: String
    lateinit var placeID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)


        val bundle = intent.extras
        if (bundle != null) {

            lat = bundle.getDouble(KEY_LAT)
            lng = bundle.getDouble(KEY_LNG)
            title = bundle.getString(KEY_TITLE)!!
            placeID = bundle.getString(KEY_PLACEID)!!
        } else
            finish()

        setupTitleBar()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.ui_place_detailAct_mapV) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val placeDetails = locationApi.getPlaceDetails(
            apiKey = API_KEY,
            placeId = placeID
        )

        placeDetails.enqueue(object : Callback<LocationData> {
            override fun onFailure(call: Call<LocationData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<LocationData>, response: Response<LocationData>) {
                response.body()?.result?.photos?.let {
                    setupPhotos(it)
                }
            }
        })
    }

    private fun setupPhotos(datas: List<PhotoData>) {
        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        ui_place_detailAct_photosListV.addItemDecoration(dividerItemDecoration)
        ui_place_detailAct_photosListV.layoutManager = layoutManager

        val adapter = PhotoGalleryAdapter(datas)
        ui_place_detailAct_photosListV.adapter = adapter
    }

    private fun setupTitleBar() {
        setSupportActionBar(ui_place_detailAct_toolBarV)
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setupLocationOnMap(lat, lng, title)
    }
}
