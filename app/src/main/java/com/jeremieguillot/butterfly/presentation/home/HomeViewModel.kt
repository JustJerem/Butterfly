package com.jeremieguillot.butterfly.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeremieguillot.butterfly.domain.interactors.GetButterflies
import com.jeremieguillot.butterfly.presentation.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getButterflies: GetButterflies
) : ViewModel() {

    var state by mutableStateOf(HomeContract.State())


    private val _errorEvents = Channel<HomeContract.Error>()
    val errorEvents = _errorEvents.receiveAsFlow()

    init {
        onEvent(HomeContract.Event.RequestButterflies)
    }

    fun onEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.RequestButterflies -> requestButterflies()
            is HomeContract.Event.SearchButterflies -> filterButterflies(event.query)
            is HomeContract.Event.ResetSearch -> resetSearch()
            is HomeContract.Event.ToggleSearchBar -> toggleSearchBarVisibility()
        }
    }

    fun toggleSearchBarVisibility() {
        state = state.copy(isSearchBarVisible = !state.isSearchBarVisible)
    }

    private fun requestButterflies() {
        getButterflies().onEach {
            state = when (it) {
                is Result.Failure -> {
                    _errorEvents.send(HomeContract.Error.UnknownIssue)
                    state.copy(isViewLoading = false)
                }

                Result.Loading -> state.copy(isViewLoading = true)
                is Result.Success -> state.copy(
                    isViewLoading = false,
                    butterflies = it.value,
                    filteredButterflies = it.value
                )
            }
        }.launchIn(viewModelScope)
    }


    private fun filterButterflies(query: String) {
        // Filtrer la liste des papillons en fonction du texte de recherche
        val filteredButterflies = if (query.isNotBlank()) {
            state.butterflies.filter { it.commonName.contains(query, ignoreCase = true) }
        } else {
            state.butterflies
        }
        state = state.copy(filteredButterflies = filteredButterflies, searchText = query)
    }

    private fun resetSearch() {
        state = state.copy(filteredButterflies = state.butterflies, searchText = "")
    }
}