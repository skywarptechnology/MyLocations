package com.sky.mylocations.support

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sky.mylocations.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_places.view.*
import kotlinx.android.synthetic.main.item_map_photolist.view.*

/**
 * Created by skyalligator on 1/20/19.
 * 3:41 PM
 */

/**
 * places list adapter
 *
 * @param placesList list of LocationDetailData
 * @param onClick listener for on item click
 */
class PlacesListAdapter(val placesList: List<LocationDetailData>, val onClick: (LocationDetailData) -> Unit) :
    RecyclerView.Adapter<PlacesListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val itemV = LayoutInflater.from(parent.context).inflate(R.layout.item_list_places, parent, false)
        return Holder(itemV, onClick)
    }

    override fun getItemCount() = placesList.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setValToViews(placesList[position])
    }

    class Holder(itemV: View, val onClickItem: (LocationDetailData) -> Unit) : RecyclerView.ViewHolder(itemV) {

        fun setValToViews(placesData: LocationDetailData) {
            Picasso.get()
                .load(placesData.icon)
                .placeholder(R.drawable.ic_location)
                .into(itemView.ui_item_PlaceList_IconV)

            itemView.ui_item_PlaceList_nameV.text = placesData.name
            itemView.ui_item_PlaceList_addressV.text = placesData.vicinity
            itemView.setOnClickListener {
                onClickItem(placesData)
            }
        }
    }
}

/**
 * photo gallery adapter
 *
 * @param photos list of PhotoData
 */
class PhotoGalleryAdapter(val photos: List<PhotoData>) :
    RecyclerView.Adapter<PhotoGalleryAdapter.PhotoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {

        val itemV = LayoutInflater.from(parent.context).inflate(R.layout.item_map_photolist, parent, false)
        return PhotoHolder(itemV)
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.setValToViews(photos[position])
    }

    class PhotoHolder(itemV: View) : RecyclerView.ViewHolder(itemV) {

        fun setValToViews(photo: PhotoData) {
            val uri = Uri.parse("${BASE_URL}photo?maxwidth=600&photoreference=${photo.photo_reference}&key=$API_KEY")
            Picasso.get()
                .load(uri)
                .error(R.drawable.ic_location)
                .into(itemView.ui_itemPhotoList_imgV)

        }
    }
}