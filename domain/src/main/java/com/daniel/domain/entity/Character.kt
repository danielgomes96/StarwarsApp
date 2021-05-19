package com.daniel.domain.entity

import java.io.Serializable

data class Character(
    val name: String,
    val birthYear: String,
    val height: Int,
    val homeWorld: String,
    val speciesList: List<String>,
    val filmsList: List<String>
) : Serializable
