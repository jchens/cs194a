package com.example.a3_yelpclone

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
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

        handleIntent(intent, yelpService, restaurants, adapter)


        // async
//        yelpService.searchRestaurants("Bearer $API_KEY", "mexican", "nyc").enqueue(object : Callback<YelpSearchResult> {
//            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
//                Log.i(TAG, "onResponse: $response")
//
//                // check for valid YelpSearchResult from the response
//                val body = response.body()
//                if (body == null) {
//                    Log.w(TAG, "invalid response body from Yelp API :(")
//                    return
//                }
//
//                // add data to list
//                restaurants.addAll(body.restaurants)
//                // notify adapter that data has changed
//                adapter.notifyDataSetChanged()
//            }
//
//            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
//                Log.i(TAG, "onFailure $t")
//            }
//        })

    }

    private fun handleIntent(intent: Intent,  yelpService: YelpService, restaurants: MutableList<YelpRestaurant>, adapter: RestaurantsAdapter) {

        if (Intent.ACTION_SEARCH == intent.action) {
            tvPlaceholder.visibility = View.INVISIBLE
            val query = intent.getStringExtra(SearchManager.QUERY)
            //use the query to search your data somehow
            if (query != null) {
                yelpService.searchRestaurants("Bearer $API_KEY", query, "nyc").enqueue(object : Callback<YelpSearchResult> {
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
                        // notify adapter that data has changed
                        adapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                        Log.i(TAG, "onFailure $t")
                    }
                })
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }
}