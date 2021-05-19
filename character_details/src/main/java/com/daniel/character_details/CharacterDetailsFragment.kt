package com.daniel.character_details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.daniel.base.BottomSheetHelperCallback
import com.daniel.base.extension.findNavController
import com.daniel.base.extension.formatHeight
import com.daniel.base.extension.gone
import com.daniel.base.extension.viewBinding
import com.daniel.base.extension.visible
import com.daniel.character_details.databinding.FragmentCharacterDetailsBinding
import com.daniel.character_details.di.characterDetailsModule
import com.daniel.domain.entity.Character
import com.daniel.domain.entity.Planet
import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.entity.Species
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_films.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

@ExperimentalCoroutinesApi
class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    private val args: CharacterDetailsFragmentArgs by navArgs()
    private val binding by viewBinding(FragmentCharacterDetailsBinding::bind)
    private val viewModel by viewModel<CharacterDetailsViewModel>()
    private val bottomSheetBehavior by lazy {
        BottomSheetBehavior.from(binding.bottomSheet.bottomSheetFilmsRoot)
    }
    private val filmsAdapter by lazy {
        CharacterFilmsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(characterDetailsModule)
        setupToolbar()
        setupCharacterDetails()
        requestAdditionalDataFromCharacter(args.characterProperties)
        setupObservers()
        setupFilmsAdapter()
        setupBottomSheet()
    }

    private fun setupCharacterDetails() = with(binding) {
        birthYear.text = String.format(
            getString(R.string.birthyear_text), args.characterProperties.birthYear
        )
        height.text = args.characterProperties.height.formatHeight()
    }

    private fun requestAdditionalDataFromCharacter(character: Character) {
        viewModel.apply {
            getSpeciesDetails(character.speciesList)
            getPlanetDetails(character.homeWorld)
            getFilms(character.filmsList)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.filmsLiveData.observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        showFilmsLoadingView()
                    }
                    is ResultWrapper.Success -> {
                        binding.progressBarBottomSheet.gone()
                        binding.bottomSheet.bottomSheetFilmsRoot.visible()
                        filmsAdapter.setupList(result.value)
                    }
                    else -> showErrorMessage()
                }
            })
            viewModel.speciesLiveData.observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        binding.shimmerSpecies.startShimmer()
                    }
                    is ResultWrapper.Success -> {
                        showSpeciesDetails(result.value)
                    }
                    is ResultWrapper.Empty -> {
                        binding.shimmerSpecies.stopShimmer()
                        binding.shimmerSpecies.gone()
                    }
                    else -> showErrorMessage()
                }
            })
            viewModel.planetLiveData.observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        binding.shimmerPlanet.startShimmer()
                    }
                    is ResultWrapper.Success -> {
                        showPlanetDetails(result.value)
                    }
                    is ResultWrapper.Empty -> {
                        binding.shimmerPlanet.stopShimmer()
                        binding.shimmerPlanet.gone()
                    }
                    else -> showErrorMessage()
                }
            })
            viewModel.bottomSheetStateLiveData.observe(viewLifecycleOwner, Observer { bottomSheetState ->
                bottomSheetBehavior.state = bottomSheetState
            })
        }
    }

    private fun showErrorMessage() {
        context?.let {
            Toast.makeText(context, getString(R.string.error_message), Toast.LENGTH_LONG).show()
        }
    }

    private fun setupFilmsAdapter() {
        binding.bottomSheet.bottomSheetFilmsRecyclerView.apply {
            adapter = filmsAdapter
        }
    }

    private fun showFilmsLoadingView() {
        binding.progressBarBottomSheet.visible()
    }

    private fun showPlanetDetails(planet: Planet) = with(binding) {
        shimmerPlanet.stopShimmer()
        shimmerPlanet.gone()
        planetHeader.visible()
        planetName.visible()
        planetPopulation.visible()
        planetPopulation.text = String.format(
            getString(R.string.planet_population, planet.population)
        )
        planetName.text = String.format(
            getString(R.string.planet_name, planet.name)
        )
    }

    private fun showSpeciesDetails(value: List<Species>) = with(binding) {
        shimmerSpecies.stopShimmer()
        shimmerSpecies.gone()
        speciesHeader.visible()
        speciesName.visible()
        speciesLanguage.visible()
        speciesName.text = String.format(getString(R.string.species_name, value[0].name))
        speciesLanguage.text = String.format(getString(R.string.species_language, value[0].language))
    }

    private fun setupToolbar() {
        binding.toolbar.setupWithNavController(findNavController())
        binding.toolbar.title = args.characterProperties.name
    }

    private fun setupBottomSheet() {
        binding.bottomSheet.bottomSheetFilmsRoot.setOnClickListener {
            viewModel.updateBottomSheetState(bottomSheetBehavior.state)
        }

        bottomSheetBehavior.addBottomSheetCallback(
            BottomSheetHelperCallback(
                onCollapsed = {
                    context?.let {
                        binding.bottomSheet.bottomSheetFilmsRoot.filmsListHeader.bottomSheetStateImage
                            .setImageDrawable(
                                ContextCompat.getDrawable(it, R.drawable.ic_arrow_expand)
                            )
                    }
                },
                onExpanded = {
                    context?.let {
                        binding.bottomSheet.bottomSheetFilmsRoot.filmsListHeader.bottomSheetStateImage
                            .setImageDrawable(
                                ContextCompat.getDrawable(it, R.drawable.ic_arrow_collapse)
                            )
                    }
                }
            )
        )
    }

    @ExperimentalCoroutinesApi
    override fun onDestroyView() {
        super.onDestroyView()
        unloadKoinModules(characterDetailsModule)
    }
}
