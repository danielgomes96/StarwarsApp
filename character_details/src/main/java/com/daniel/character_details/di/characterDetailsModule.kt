package com.daniel.character_details.di

import com.daniel.character_details.CharacterDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val characterDetailsModule = module {
    viewModel {
        CharacterDetailsViewModel(
            get(),
            get(),
            get()
        )
    }
}
