package com.example.mymaps.models

import java.io.Serializable

data class Place(val title: String, val description: String, val lat: Double, val long: Double) : Serializable