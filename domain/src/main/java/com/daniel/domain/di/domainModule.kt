package com.daniel.domain.di

import com.daniel.domain.usecase.GetFilms
import com.daniel.domain.usecase.GetFilmsImpl
import com.daniel.domain.usecase.GetPlanetDetails
import com.daniel.domain.usecase.GetPlanetDetailsImpl
import com.daniel.domain.usecase.GetSpeciesDetails
import com.daniel.domain.usecase.GetSpeciesDetailsImpl
import com.daniel.domain.usecase.SearchCharacter
import com.daniel.domain.usecase.SearchCharacterImpl
import org.koin.dsl.module

val domainModule = module {
    factory {
        SearchCharacterImpl(
            get()
        ) as SearchCharacter
    }

    factory {
        GetPlanetDetailsImpl(
            get()
        ) as GetPlanetDetails
    }

    factory {
        GetFilmsImpl(
            get()
        ) as GetFilms
    }

    factory {
        GetSpeciesDetailsImpl(
            get()
        ) as GetSpeciesDetails
    }
}
