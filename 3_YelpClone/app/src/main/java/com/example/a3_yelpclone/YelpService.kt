package com.example.a3_yelpclone

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


public interface YelpService {
    // Request method and URL specified in the annotation
    @GET("businesses/search")
    fun searchRestaurants(
            @Header("Authorization") authHeader: String,
            @Query("term") searchTerm: String,
            @Query("location") location: String
    ) : Call<YelpSearchResult>
}