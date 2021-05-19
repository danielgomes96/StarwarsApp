package com.daniel.data.di

import com.daniel.data.repository.CharacterRepositoryImpl
import com.daniel.data.repository.FilmRepositoryImpl
import com.daniel.data.repository.PlanetRepositoryImpl
import com.daniel.data.repository.SpeciesRepositoryImpl
import com.daniel.domain.repository.CharacterRepository
import com.daniel.domain.repository.FilmRepository
import com.daniel.domain.repository.PlanetRepository
import com.daniel.domain.repository.SpeciesRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        CharacterRepositoryImpl(
            get()
        ) as CharacterRepository
    }

    factory {
        PlanetRepositoryImpl(
            get()
        ) as PlanetRepository
    }

    factory {
        SpeciesRepositoryImpl(
            get()
        ) as SpeciesRepository
    }

    factory {
        FilmRepositoryImpl(
            get()
        ) as FilmRepository
    }
}
