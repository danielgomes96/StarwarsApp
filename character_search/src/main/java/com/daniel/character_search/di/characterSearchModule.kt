package com.daniel.character_search.di

import com.daniel.character_search.CharacterSearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val characterSearchModule = module {
    viewModel {
        CharacterSearchViewModel(get())
    }
}
