package com.example.a3_yelpclone

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://api.yelp.com/v3/"
private const val TAG = "MainActivity"
private const val API_KEY = "1KY93sRFU95zRDERxDFE73LuIIoz7B8wDZPK8MFHRZttQQxIM0lHcp9esPkW7RLOPlV5DdPWhu_p0y-lisnwvaDkimVbjv_WkclEKVOhivKBf_-fCjM82VAtzY-nX3Yx"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val restaurants = mutableListOf<YelpRestaurant>()
        val adapter = RestaurantsAdapter(this, restaurants)
        // rv from view
        rvRestaurants.adapter = adapter
        rvRestaurants.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val yelpService = retrofit.create(YelpService::class.java)

        // async
        yelpService.searchRestaurants("Bearer $API_KEY", "mexican", "nyc").enqueue(object : Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.i(TAG, "onResponse: $response")

                // check for valid YelpSearchResult from the response
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "invalid response body from Yelp API :(")
                    return
                }

                // add data to list
                restaurants.addAll(body.restaurants)
                Log.i(TAG, "results: $body")
                Log.i(TAG, "restaurants now: $restaurants")

                // notify adapter that data has changed
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }

        })

    }
}