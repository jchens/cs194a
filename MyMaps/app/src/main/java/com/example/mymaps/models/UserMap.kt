package com.example.mymaps.models

import java.io.Serializable

// needs title, markers
data class UserMap(val title: String, val places: List<Place>) : Serializable