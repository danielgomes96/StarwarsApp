package com.daniel.character_search

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.daniel.base.base.BaseViewModel
import com.daniel.domain.entity.Character
import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.usecase.SearchCharacter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class CharacterSearchViewModel(
    private val searchCharacter: SearchCharacter
) : BaseViewModel() {

    private val _characterListLiveData = MutableStateFlow<ResultWrapper<List<Character>>>(ResultWrapper.InitialState())
    val characterListLiveData: StateFlow<ResultWrapper<List<Character>>>
        get() = _characterListLiveData

    private val _switchThemeLiveData = MutableLiveData<Int>()
    val switchThemeLiveData: LiveData<Int>
        get() = _switchThemeLiveData

    fun getCharacters(name: String) = launch {
        searchCharacter.execute(name)
            .collect { characterList ->
                _characterListLiveData.value = characterList
            }
    }

    fun switchTheme(checked: Boolean) {
        if (checked) {
            _switchThemeLiveData.postValue(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            _switchThemeLiveData.postValue(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
