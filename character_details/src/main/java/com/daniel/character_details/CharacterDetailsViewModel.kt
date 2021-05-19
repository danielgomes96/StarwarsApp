package com.daniel.character_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.daniel.base.base.BaseViewModel
import com.daniel.domain.entity.Film
import com.daniel.domain.entity.Planet
import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.entity.Species
import com.daniel.domain.usecase.GetFilms
import com.daniel.domain.usecase.GetPlanetDetails
import com.daniel.domain.usecase.GetSpeciesDetails
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class CharacterDetailsViewModel(
    private val getPlanetDetails: GetPlanetDetails,
    private val getFilms: GetFilms,
    private val getSpeciesDetails: GetSpeciesDetails
) : BaseViewModel() {

    private val _planetLiveData = MutableLiveData<ResultWrapper<Planet>>()
    val planetLiveData: LiveData<ResultWrapper<Planet>>
        get() = _planetLiveData

    private val _filmsLiveData = MutableLiveData<ResultWrapper<List<Film>>>()
    val filmsLiveData: LiveData<ResultWrapper<List<Film>>>
        get() = _filmsLiveData

    private val _speciesLiveData = MutableLiveData<ResultWrapper<List<Species>>>()
    val speciesLiveData: LiveData<ResultWrapper<List<Species>>>
        get() = _speciesLiveData

    private val _bottomSheetStateLiveData = MutableLiveData<Int>()
    val bottomSheetStateLiveData: LiveData<Int>
        get() = _bottomSheetStateLiveData

    fun getPlanetDetails(homeWorld: String) = launch {
        getPlanetDetails.execute(homeWorld)
            .collect { planetDetails ->
                _planetLiveData.postValue(planetDetails)
            }
    }

    fun getFilms(filmsList: List<String>) = launch {
        getFilms.execute(filmsList)
            .collect { filmsList ->
                _filmsLiveData.postValue(filmsList)
            }
    }

    fun getSpeciesDetails(speciesList: List<String>) = launch {
        if (speciesList.isNotEmpty()) {
            getSpeciesDetails.execute(speciesList)
                .collect { speciesList ->
                    _speciesLiveData.postValue(speciesList)
                }
        } else {
            _speciesLiveData.postValue(ResultWrapper.Empty)
        }
    }

    fun updateBottomSheetState(state: Int) {
        if (state == BottomSheetBehavior.STATE_COLLAPSED) {
            _bottomSheetStateLiveData.postValue(BottomSheetBehavior.STATE_EXPANDED)
        } else {
            _bottomSheetStateLiveData.postValue(BottomSheetBehavior.STATE_COLLAPSED)
        }
    }
}
