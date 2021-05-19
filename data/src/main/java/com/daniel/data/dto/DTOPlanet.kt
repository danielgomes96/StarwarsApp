package com.daniel.data.dto

import com.google.gson.annotations.SerializedName

data class DTOPlanet(
    val name: String,
    @SerializedName("population")
    val population: String
)
