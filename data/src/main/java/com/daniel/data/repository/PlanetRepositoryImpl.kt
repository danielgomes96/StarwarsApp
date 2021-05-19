package com.daniel.data.repository

import com.daniel.data.mapper.PlanetMapper
import com.daniel.data.service.StarwarsService
import com.daniel.domain.entity.Planet
import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.repository.PlanetRepository
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class PlanetRepositoryImpl(
    private val starwarsService: StarwarsService
) : PlanetRepository {
    override suspend fun getPlanetDetails(name: String): Flow<ResultWrapper<Planet>> = flow {
        emit(ResultWrapper.Loading)
        runCatching {
            starwarsService.getPlanetDetails(name)
        }.onSuccess {
            emit(ResultWrapper.Success(PlanetMapper().transform(it.body())))
        }.onFailure { throwable ->
            when (throwable) {
                is IOException -> emit(ResultWrapper.NetworkError)
                is HttpException -> emit(ResultWrapper.GenericError(throwable.code(), throwable.message))
                else -> emit(ResultWrapper.GenericError(null, throwable.message))
            }
        }
    }
}
