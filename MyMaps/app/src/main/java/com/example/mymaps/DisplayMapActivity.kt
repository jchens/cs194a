package com.example.mymaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.example.mymaps.models.UserMap

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ui.IconGenerator

private const val TAG = "DisplayMapActivity.kt"

class DisplayMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var userMap: UserMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_map)

        // cast as UserMap to get our data structure back
        userMap = intent.getSerializableExtra(EXTRA_USER_MAP) as UserMap
        supportActionBar?.title = userMap.title

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        Log.i(TAG, "onMapReady: ${userMap.title}")

        mMap = googleMap
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        val boundsBuilder = LatLngBounds.Builder()
        for (place in userMap.places) {
            val coords = LatLng(place.lat, place.long)
//            val markerBitmap = AppCompatResources.getDrawable(this, R.drawable.ic_baseline_push_pin_24)?.toBitmap()
//            val customMarker = BitmapDescriptorFactory.fromBitmap(markerBitmap)

            val iconGenerator = IconGenerator(this)
            iconGenerator.setStyle(IconGenerator.STYLE_WHITE);
            val bubbleBitmap = iconGenerator.makeIcon("📌");
            val customBubble = BitmapDescriptorFactory.fromBitmap(bubbleBitmap)

            mMap.addMarker(MarkerOptions()
                .position(coords)
                .title(place.title)
                .snippet(place.description)
                .icon(customBubble)
            )
            boundsBuilder.include(coords)
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 1000, 1000, 100))
    }
}