package com.sky.mylocations.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.sky.mylocations.*
import com.sky.mylocations.support.*
import kotlinx.android.synthetic.main.activity_places.*


/**
 * Created by skyalligator on 1/19/19.
 * 8:50 PM
 */
/**
 * shows list of places for the locality and for search
 */
class PlacesActivity : CoroutineActivity(), SearchView.OnQueryTextListener {

    /**
     * action bar search listener
     */
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrBlank()) {
            fetchData(query)
            return true
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    /**
     * listener for particular place selection from the places list
     */
    private val onClick: (LocationDetailData) -> Unit = { data ->
        val intent = Intent(this@PlacesActivity, MapsActivity::class.java)
        intent.putExtra(MapsActivity.KEY_TITLE, data.name)
        intent.putExtra(MapsActivity.KEY_ADDRESS, data.vicinity)
        intent.putExtra(MapsActivity.KEY_PLACEID, data.place_id)
        intent.putExtra(MapsActivity.KEY_LAT, data.geometry.location.lat)
        intent.putExtra(MapsActivity.KEY_LNG, data.geometry.location.lng)
        startActivity(intent)
    }

    /**
     * option menu setup for search location
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_places_activity, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        (menu?.findItem(R.id.action_search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isSubmitButtonEnabled = true
            setOnQueryTextListener(this@PlacesActivity)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places)

        API_KEY = getString(R.string.google_maps_key) // initiate api key from the resource

        setSupportActionBar(ui_act_places_toolBarV)
        initList()
        fetchData("food")
    }

    /**
     * coroutine call with retro implementation for
     * search places by given search query and update the list view
     */
    private fun fetchData(search: String) {

        //basically ignores the exception if any
        launchWithoutEx {

            ui_act_places_ProgressV.visibility = View.VISIBLE

            val placeDetails = locationApi.getPlaces(radius = "1000", location = "19,-99", types = search)
            val data = placeDetails.callApi()
            data?.let {
                setListData(it.results)
            }
            ui_act_places_ProgressV.visibility = View.INVISIBLE
        }
    }


    /**
     * sets list data to the list view adapter
     */
    private fun setListData(list: List<LocationDetailData>) {
        ui_act_places_ListV.adapter = PlacesListAdapter(list, onClick)
    }

    /**
     * list view initialization
     */
    private fun initList() {
        val layoutManager = LinearLayoutManager(this)
        ui_act_places_ListV.layoutManager = layoutManager
    }
}