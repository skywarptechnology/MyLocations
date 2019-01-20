package com.sky.mylocations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_places.view.*

/**
 * Created by skyalligator on 1/20/19.
 * 3:41 PM
 */
class PlacesListAdapter(val placesList:List<PlaceDetailsData>):RecyclerView.Adapter<PlacesListAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
       return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_places, parent, false))
    }

    override fun getItemCount()=placesList.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setValToViews(placesList[position])
    }

    class Holder(itemV: View) : RecyclerView.ViewHolder(itemV) {

        fun setValToViews(placesData: PlaceDetailsData) {
            Picasso.get().load(placesData.icon).placeholder(R.drawable.ic_location).into(itemView.ui_item_PlaceList_IconV)
            itemView.ui_item_PlaceList_nameV.text=placesData.name
            itemView.ui_item_PlaceList_addressV.text=placesData.vicinity
        }
    }
}