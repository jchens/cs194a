package com.example.mymaps

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymaps.models.UserMap


private const val TAG = "MapsAdapter.kt"
class MapsAdapter(val context: Context, val userMaps: List<UserMap>, val onoClickListener: OnClickListener) : RecyclerView.Adapter<MapsAdapter.ViewHolder>() {
    interface OnClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapsAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_user_map, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // lookup correct userMap
        val userMap = userMaps[position]

        holder.itemView.setOnClickListener() {
            Log.i(TAG, "Tapped on position #$position")
            onoClickListener.onItemClick(position)
        }

        // we know tv's id is text1
        val tvTitle = holder.itemView.findViewById<TextView>(R.id.tvMapTitle)
        tvTitle.text = userMap.title
        val tvNumPlaces = holder.itemView.findViewById<TextView>(R.id.tvNumPlaces)
        val noun = if (userMap.places.size == 1) "place" else "places"
        tvNumPlaces.text = "${userMap.places.size.toString()} $noun to explore"
    }

    override fun getItemCount(): Int {
        return userMaps.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
