package com.daniel.data.dto

import com.google.gson.annotations.SerializedName

data class DTOCharacter(
    @SerializedName("name")
    val name: String?,
    @SerializedName("birth_year")
    val birthYear: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("homeworld")
    val homeWorld: String?,
    @SerializedName("species")
    val speciesList: List<String>?,
    @SerializedName("films")
    val filmsList: List<String>?
)
