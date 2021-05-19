package com.daniel.data.dto

import com.google.gson.annotations.SerializedName

data class DTOFilm(
    @SerializedName("title")
    val title: String,
    @SerializedName("opening_crawl")
    val description: String
)
