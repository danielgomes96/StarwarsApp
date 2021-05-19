package com.daniel.domain.usecase

import com.daniel.domain.entity.Film
import com.daniel.domain.entity.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface GetFilms {
    suspend fun execute(filmsList: List<String>): Flow<ResultWrapper<List<Film>>>
}
