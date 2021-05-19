package com.daniel.data.dto

import com.google.gson.annotations.SerializedName

data class DTOSpecies(
    @SerializedName("name")
    val name: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("homeworld")
    val homeWorld: String?
)
