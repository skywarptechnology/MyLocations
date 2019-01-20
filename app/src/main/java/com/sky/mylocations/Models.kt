package com.sky.mylocations

/**
 * Created by skyalligator on 1/20/19.
 * 1:09 PM
 */
data class PlacesData(
    val id: String,
    val results: List<PlaceDetailsData>,
    val status: String
)

data class PlaceDetailsData(
    val id: String,
    val icon: String,
    val name: String,
    val photos: Any,
    val place_id: String,
    val scope: String,
    val types: List<String>,
    val vicinity: String
)

data class PlusCodeData(
    val compound_code: String,
    val global_code: String
)