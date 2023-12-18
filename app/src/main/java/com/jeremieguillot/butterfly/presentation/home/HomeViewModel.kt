package com.jeremieguillot.butterfly.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeremieguillot.butterfly.domain.interactors.GetButterflies
import com.jeremieguillot.butterfly.domain.model.ButterflyColor
import com.jeremieguillot.butterfly.domain.model.ConservationStatus
import com.jeremieguillot.butterfly.domain.model.Frequency
import com.jeremieguillot.butterfly.domain.model.HabitatType
import com.jeremieguillot.butterfly.domain.model.MonthEnum
import com.jeremieguillot.butterfly.domain.model.ProtectionStatus
import com.jeremieguillot.butterfly.domain.model.Region
import com.jeremieguillot.butterfly.presentation.data.Result
import com.jeremieguillot.butterfly.presentation.filter.CategoryType
import com.jeremieguillot.butterfly.presentation.filter.FilterItem
import com.jeremieguillot.butterfly.presentation.home.composable.ButterflyCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getButterflies: GetButterflies,
) : ViewModel() {

    var state by mutableStateOf(HomeContract.State())

    private val _errorEvents = Channel<HomeContract.Error>()
    val errorEvents = _errorEvents.receiveAsFlow()


    //Filter

    var selectedCategory by mutableStateOf(CategoryType.FLIGHT_MONTH)
    var selectedFlightMonths by mutableStateOf<List<MonthEnum>>(emptyList())
    var selectedConservationStatus by mutableStateOf<List<ConservationStatus>>(emptyList())
    var selectedHabitatType by mutableStateOf<List<HabitatType>>(emptyList())
    var selectedProtectionStatus by mutableStateOf<List<ProtectionStatus>>(emptyList())
    var selectedFrequencies by mutableStateOf<List<Frequency>>(emptyList())
    var selectedRegions by mutableStateOf<List<Region>>(emptyList())
    var selectedColors by mutableStateOf<List<ButterflyColor>>(emptyList())

    init {
        requestButterflies(ButterflyCategory.PAPILIONIDES)
    }

    fun onEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.SearchButterflies -> filterButterflies(event.query)
            HomeContract.Event.ToggleSearchBar -> toggleSearchBarVisibility()
            is HomeContract.Event.ButterflyCategorySelected -> updateSelectedFamily(event.family)
            is HomeContract.Event.SetSelectedCategory -> updateSelectedSettingCategory(event.category)
        }
    }


    fun onItemSelected(item: FilterItem) {
        when (item.type) {
            CategoryType.FREQUENCY -> selectedFrequencies = updateSelectedItem(
                Frequency.values().first { it.displayName == item.name },
                selectedFrequencies
            )

            CategoryType.REGION -> selectedRegions = updateSelectedItem(
                Region.values().first { it.displayName == item.name },
                selectedRegions
            )

            else -> TODO()
        }
    }

    fun <T> updateSelectedItem(item: T, selectedItems: List<T>): List<T> {
        return if (selectedItems.contains(item)) {
            selectedItems - item
        } else {
            selectedItems + item
        }
    }

    fun getSelectedItems(): List<FilterItem> {
        when (selectedCategory) {
//            CategoryType.FLIGHT_MONTH -> return selectedFlightMonths
//            CategoryType.CONSERVATION_STATUS -> return selectedConservationStatus
//            CategoryType.HABITAT_TYPE -> return selectedHabitatType
//            CategoryType.PROTECTION_STATUS -> return selectedProtectionStatus
            CategoryType.FREQUENCY -> return Frequency.values().map { frequency ->
                FilterItem(
                    frequency.displayName,
                    selectedFrequencies.contains(frequency),
                    CategoryType.FREQUENCY
                )
            }

            CategoryType.REGION -> return Region.values().map { frequency ->
                FilterItem(
                    frequency.displayName,
                    selectedRegions.contains(frequency),
                    CategoryType.FREQUENCY
                )
            }
//            CategoryType.BUTTERFLY_COLOR -> return selectedColors
            else -> TODO()
        }
    }

    private fun updateSelectedSettingCategory(category: CategoryType) {
        selectedCategory = category
    }

    private fun onResetFilter() {
        selectedFlightMonths = emptyList()
        selectedConservationStatus = emptyList()
        selectedHabitatType = emptyList()
        selectedProtectionStatus = emptyList()
        selectedFrequencies = emptyList()
        selectedRegions = emptyList()
        selectedColors = emptyList()
    }

    private fun updateSelectedFamily(category: ButterflyCategory) {
        state = state.copy(family = category)
        requestButterflies(category)
    }


    private fun toggleSearchBarVisibility() {
        state = state.copy(isSearchBarVisible = !state.isSearchBarVisible)
        if (!state.isSearchBarVisible) {
            resetSearch()
        }
    }

    private fun requestButterflies(family: ButterflyCategory) {
        getButterflies(family.displayName).onEach {
            state = when (it) {
                is Result.Failure -> {
                    _errorEvents.send(HomeContract.Error.UnknownIssue)
                    state.copy(isViewLoading = false)
                }

                Result.Loading -> state.copy(isViewLoading = true)
                is Result.Success -> {
                    state.copy(
                        isViewLoading = false,
                        butterflies = it.value,
                        filteredButterflies = it.value
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun filterButterflies(query: String) {
        getButterflies(name = query).onEach {
            state = when (it) {
                is Result.Failure -> {
                    _errorEvents.send(HomeContract.Error.UnknownIssue)
                    state.copy(isViewLoading = false)
                }

                Result.Loading -> state.copy(isViewLoading = true)
                is Result.Success -> {
                    state.copy(
                        isViewLoading = false,
                        filteredButterflies = it.value
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun resetSearch() {
        state = state.copy(filteredButterflies = state.butterflies)
    }
}