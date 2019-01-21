package com.sky.mylocations

/**
 * Created by skyalligator on 1/20/19.
 * 1:09 PM
 */

/**
 * Model/POJO Class
 */
data class PlacesData(
    val id: String,
    val results: List<LocationDetailData>,
    val status: String
)

/**
 * Model/POJO Class
 */
data class LocationData(
    val id: String,
    val result: LocationDetailData,
    val status: String
)

/**
 * Model/POJO Class
 */
data class LocationDetailData(
    val id: String,
    val icon: String,
    val name: String,
    val geometry: Geometry,
    val photos: List<PhotoData>,
    val place_id: String,
    val scope: String,
    val types: List<String>,
    val vicinity: String
)

/**
 * Model/POJO Class
 */
data class Geometry(
    val location: Location
)

/**
 * Model/POJO Class
 */
data class Location(
    val lat: Double,
    val lng: Double
)

/**
 * Model/POJO Class
 */
data class PhotoData(
    val photo_reference: String
)