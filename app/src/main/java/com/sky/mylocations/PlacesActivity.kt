package com.sky.mylocations

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_places.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by skyalligator on 1/19/19.
 * 8:50 PM
 */
/**
 * shows list of places for the locality and for search
 */
class PlacesActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrBlank()) {
            callApiForSearchPlaces(query)
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
    val onClick: (LocationDetailData) -> Unit = { data ->
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

        API_KEY = getString(R.string.google_maps_key)

        setSupportActionBar(ui_act_places_toolBarV)

        initList()
        callApiForSearchPlaces("food")
    }

    /**
     * search places by given search query and update the list view
     */
    private fun callApiForSearchPlaces(search: String) {
        val placeDetails = locationApi.getPlaces(
            radius = "1000",
            location = "19,-99",
            types = search
        )

        placeDetails.enqueue(object : Callback<PlacesData> {
            override fun onFailure(call: Call<PlacesData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<PlacesData>, response: Response<PlacesData>) {
                val data = response.body()
                data.lg()
                data?.results?.let {
                    setListData(it)
                }
            }
        })
    }

    /**
     * sets list data to the list view adapter
     */
    fun setListData(list: List<LocationDetailData>) {
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