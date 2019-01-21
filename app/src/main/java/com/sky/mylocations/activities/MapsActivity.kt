package com.sky.mylocations.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.sky.mylocations.R
import com.sky.mylocations.support.*
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : CoroutineActivity(), OnMapReadyCallback {

    /**
     * key constants for argument passing from other activity
     */
    companion object {
        val KEY_LAT = "lat"
        val KEY_LNG = "lng"
        val KEY_TITLE = "title"
        val KEY_ADDRESS = "address"
        val KEY_PLACEID = "place_id"
    }

    //location variables
    var lat: Double = 0.0
    var lng: Double = 0.0

    //ui variables
    lateinit var title: String
    lateinit var address: String
    lateinit var placeID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        getArguments()
        setupUI()
        callApiForLocationPhotos()
    }

    /**
     * get intent arguments for setup UI, if no extras then this activity will finish
     */
    private fun getArguments() {
        val bundle = intent.extras
        if (bundle != null) {

            lat = bundle.getDouble(KEY_LAT)
            lng = bundle.getDouble(KEY_LNG)
            title = bundle.getString(KEY_TITLE)!!
            address = bundle.getString(KEY_ADDRESS)!!
            placeID = bundle.getString(KEY_PLACEID)!!
        } else
            finish()
    }

    /**
     * sets up map, title, address text view and share icon listener
     */
    private fun setupUI() {

        setupTitleBar()

        ui_place_detailAct_detailAddrV.text = address

        ui_place_detailAct_shareV.setOnClickListener { shareAddressViaOtherApps("Place", "$title : $address") }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.ui_place_detailAct_mapV) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    /**
     * coroutine call with retro implementation for
     * photos and callback implementation
     */
    private fun callApiForLocationPhotos() {

        //basically ignores the exception if any
        launchWithoutEx {

            val placeDetails = locationApi.getPlaceDetails(apiKey = API_KEY, placeId = placeID)
            val photos = placeDetails.callApi()?.result?.photos

            if (photos != null) {

                ui_place_detailAct_noImagesTV.visibility = View.INVISIBLE
                setupPhotos(photos)
            } else {

                ui_place_detailAct_noImagesTV.visibility = View.VISIBLE
            }
        }
    }

    /**
     * setup the horizontal list view for photo gallery
     */
    private fun setupPhotos(datas: List<PhotoData>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        ui_place_detailAct_photosListV.layoutManager = layoutManager

        val adapter = PhotoGalleryAdapter(datas)
        ui_place_detailAct_photosListV.adapter = adapter
    }

    /**
     * action bar initialization with title and home button
     */
    private fun setupTitleBar() {
        setSupportActionBar(ui_place_detailAct_toolBarV)
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    /**
     * back arrow listener in actionbar
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish() // finishes this activity when the back arrow key pressed
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * called when the google map is ready
     */
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setupLocationOnMap(lat, lng, title)
    }
}
