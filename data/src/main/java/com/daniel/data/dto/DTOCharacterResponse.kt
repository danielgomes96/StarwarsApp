package com.daniel.data.dto

import com.google.gson.annotations.SerializedName

data class DTOCharacterResponse(
    @SerializedName("results")
    val characterList: List<DTOCharacter?>
)
