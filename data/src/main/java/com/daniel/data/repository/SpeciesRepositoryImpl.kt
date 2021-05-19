package com.daniel.data.repository

import com.daniel.data.mapper.SpeciesMapper
import com.daniel.data.service.StarwarsService
import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.entity.Species
import com.daniel.domain.repository.SpeciesRepository
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class SpeciesRepositoryImpl(
    private val starwarsService: StarwarsService
) : SpeciesRepository {
    override suspend fun getSpeciesDetails(urlsList: List<String>): Flow<ResultWrapper<List<Species>>> = flow {
        emit(ResultWrapper.Loading)
        val speciesList = mutableListOf<Species>()
        runCatching {
            urlsList.map { url ->
                starwarsService.getSpeciesDetails(url)
            }
        }.onSuccess { responseList ->
            if (responseList.isNotEmpty()) {
                responseList.map { response ->
                    response.body()?.let {
                        speciesList.add(SpeciesMapper().transform(it))
                    }
                }
                emit(ResultWrapper.Success(speciesList))
            } else {
                emit(ResultWrapper.Empty)
            }
        }.onFailure { throwable ->
            when (throwable) {
                is IOException -> emit(ResultWrapper.NetworkError)
                is HttpException -> emit(ResultWrapper.GenericError(throwable.code(), throwable.message))
                else -> emit(ResultWrapper.GenericError(null, throwable.message))
            }
        }
    }
}
