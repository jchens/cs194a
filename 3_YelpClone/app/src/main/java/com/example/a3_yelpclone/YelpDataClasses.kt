package com.example.a3_yelpclone

import com.google.gson.annotations.SerializedName

// top level search result -- to access lower levels, create new class
data class YelpSearchResult (
    // key (as named in response), value
    @SerializedName("total") val total: Int,
    @SerializedName("businesses") val restaurants: List<YelpRestaurant>

)

// represents 1 restaurant/business object on Yelp -- contained in array
data class YelpRestaurant (
    // not need for SerializedName if param name == our var name
    val name: String,
    val rating: Double,
    val price: String,
    @SerializedName("review_count") val numReviews: Int,
    @SerializedName("distance") val distanceInMeters: Double,
    @SerializedName("image_url") val imageUrl: String,
    val categories: List<YelpCategory>,
    val location: YelpLocation
) {
    // given distance in meters, returns distance in miles
    fun displayDistance(): String {
        val milesPerMeter = 0.000621371
        val distanceInMiles = "%.2f".format(distanceInMeters * milesPerMeter)
        return "$distanceInMiles mi"
    }
}


// represents 1 category on Yelp -- contained in arrays
data class YelpCategory (
    val title: String
)

data class YelpLocation (
    @SerializedName("address1") val address: String
)
