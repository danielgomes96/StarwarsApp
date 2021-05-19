package com.daniel.character_search

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.daniel.base.extension.findNavController
import com.daniel.base.extension.getQueryTextChangeStateFlow
import com.daniel.base.extension.gone
import com.daniel.base.extension.viewBinding
import com.daniel.base.extension.visible
import com.daniel.character_search.databinding.FragmentCharacterSearchBinding
import com.daniel.character_search.di.characterSearchModule
import com.daniel.domain.entity.Character
import com.daniel.domain.entity.ResultWrapper
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

@FlowPreview
@ExperimentalCoroutinesApi
class CharacterSearchFragment : Fragment(R.layout.fragment_character_search) {

    private val viewModel by viewModel<CharacterSearchViewModel>()
    private val binding by viewBinding(FragmentCharacterSearchBinding::bind)
    private var searchedName: String = ""
    private val characterSearchAdapter: CharacterSearchAdapter by lazy {
        CharacterSearchAdapter(::onItemClicked)
    }
    companion object {
        private const val DEFAULT_ERROR_CODE = 0
        private const val DEBOUNCE_TIME_IN_MILLIS = 2000L
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(characterSearchModule)
        setupToolbar()
        setupRecyclerView()
        setupSearchView()
        setupSwitchTheme()
        observeData()
    }

    private fun setupToolbar() {
        binding.toolbar.title = getString(R.string.toolbar_title)
    }

    private fun setupSwitchTheme() {
        binding.switchTheme.isChecked = isUsingNightModeResources()
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.switchTheme(isChecked)
        }
    }

    private fun setupSearchView() {
        lifecycleScope.launchWhenStarted {
            binding.searchView.getQueryTextChangeStateFlow()
                .debounce(DEBOUNCE_TIME_IN_MILLIS)
                .filter { query ->
                    return@filter query.isNotEmpty()
                }
                .distinctUntilChanged()
                .flowOn(Dispatchers.Default)
                .collect { name ->
                    searchedName = name
                    getCharacters(name)
                }
        }
    }

    private fun getCharacters(name: String) = with(binding) {
        emptyView.emptyViewContainer.gone()
        characterListRecyclerView.gone()
        viewModel.getCharacters(name)
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.switchThemeLiveData.observe(viewLifecycleOwner, Observer { mode ->
                AppCompatDelegate.setDefaultNightMode(mode)
            })
            viewModel.characterListLiveData.collect { result ->
                when (result) {
                    is ResultWrapper.InitialState -> {
                        showInitialStateView()
                    }
                    is ResultWrapper.Empty -> {
                        showEmptyView()
                    }
                    is ResultWrapper.Loading -> {
                        showLoadingView()
                    }
                    is ResultWrapper.Success -> {
                        showCharacterList(result.value)
                    }
                    is ResultWrapper.GenericError -> {
                        showResponseErrorView(result.code, result.errorMessage)
                    }
                    is ResultWrapper.NetworkError -> {
                        showNetworkErrorView()
                    }
                }
            }
        }
    }

    private fun showInitialStateView() {
        binding.initialView.root.visible()
    }

    private fun showNetworkErrorView() {
        binding.initialView.root.gone()
        binding.progressBar.gone()
        Snackbar.make(
            binding.fragmentCharacterSearchRoot,
            getString(R.string.no_internet_connection),
            Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.error_try_again_message)) {
            getCharacters(searchedName)
        }.show()
    }

    private fun showResponseErrorView(code: Int?, message: String?) {
        binding.initialView.root.gone()
        binding.progressBar.gone()
        Snackbar.make(
            binding.fragmentCharacterSearchRoot,
            String.format(getString(R.string.response_error_message),
                code ?: DEFAULT_ERROR_CODE,
                message ?: getString(R.string.default_error_message)
            ),
            Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.error_try_again_message)) {
            getCharacters(searchedName)
        }.show()
    }

    private fun showLoadingView() = with(binding) {
        initialView.root.gone()
        emptyView.emptyViewContainer.gone()
        characterListRecyclerView.gone()
        progressBar.visible()
    }

    private fun showEmptyView() = with(binding) {
        initialView.root.gone()
        progressBar.gone()
        emptyView.emptyViewContainer.visible()
    }

    private fun showCharacterList(characterList: List<Character>) = with(binding) {
        initialView.root.gone()
        emptyView.emptyViewContainer.gone()
        progressBar.gone()
        characterListRecyclerView.visible()
        characterSearchAdapter.setupList(characterList)
    }

    private fun setupRecyclerView() {
        binding.characterListRecyclerView.apply {
            adapter = characterSearchAdapter
        }
    }

    private fun isUsingNightModeResources(): Boolean {
        return when (resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            Configuration.UI_MODE_NIGHT_NO -> false
            Configuration.UI_MODE_NIGHT_UNDEFINED -> false
            else -> false
        }
    }

    private fun onItemClicked(character: Character) = with(character) {
        val directions = CharacterSearchFragmentDirections.actionSearchFragmentToFeatureCharacterDetails(
            Character(name, birthYear, height, homeWorld, speciesList, filmsList)
        )
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unloadKoinModules(characterSearchModule)
    }
}
