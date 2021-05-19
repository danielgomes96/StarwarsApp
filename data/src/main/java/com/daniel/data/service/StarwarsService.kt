package com.daniel.data.service

import com.daniel.data.dto.DTOCharacterResponse
import com.daniel.data.dto.DTOFilm
import com.daniel.data.dto.DTOPlanet
import com.daniel.data.dto.DTOSpecies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface StarwarsService {
    @GET("/api/people")
    suspend fun getCharacters(@Query("search") search: String): Response<DTOCharacterResponse>

    @GET
    suspend fun getPlanetDetails(@Url url: String): Response<DTOPlanet>

    @GET
    suspend fun getSpeciesDetails(@Url url: String): Response<DTOSpecies>

    @GET
    suspend fun getFilmDetails(@Url url: String): Response<DTOFilm>
}
