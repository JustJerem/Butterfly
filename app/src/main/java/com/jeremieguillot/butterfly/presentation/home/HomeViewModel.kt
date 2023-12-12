package com.jeremieguillot.butterfly.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeremieguillot.butterfly.domain.interactors.GetButterflies
import com.jeremieguillot.butterfly.domain.interactors.GetConfusionButterflies
import com.jeremieguillot.butterfly.domain.interactors.SearchButterflies
import com.jeremieguillot.butterfly.presentation.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getButterflies: GetButterflies,
    private val getConfusionButterflies: GetConfusionButterflies,
    private val searchButterflies: SearchButterflies,
) : ViewModel() {

    var state by mutableStateOf(HomeContract.State())

    private val _errorEvents = Channel<HomeContract.Error>()
    val errorEvents = _errorEvents.receiveAsFlow()

    private val _uiEvent = Channel<HomeContract.Navigation>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        onEvent(HomeContract.Event.RequestButterflies)
    }

    fun onEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.RequestButterflies -> requestButterflies()
            is HomeContract.Event.SearchButterflies -> filterButterflies(event.query)
            is HomeContract.Event.ToggleSearchBar -> toggleSearchBarVisibility()
            is HomeContract.Event.SetSelectedIndexButterfly -> setSelectedButterfly(event.index)
        }
    }

    private fun setSelectedButterfly(index: Int) {
//        val butterfly = state.butterflies[index]
//        getConfusionButterflies(butterfly).onEach {
//            when (it) {
//                is Result.Failure -> {
//                    _errorEvents.send(HomeContract.Error.UnknownIssue)
//                    state = state.copy(selectedIndexButterfly = index)
//                }
//
//                Result.Loading -> {}
//                is Result.Success -> {
//                    state =
//                        state.copy(selectedIndexButterfly = index, confusionButterflies = it.value)
//                    _uiEvent.send(HomeContract.Navigation.OpenDetail)
//                }
//            }
//        }.launchIn(viewModelScope)
    }

    private fun toggleSearchBarVisibility() {
        state = state.copy(isSearchBarVisible = !state.isSearchBarVisible)
        if (!state.isSearchBarVisible) {
            resetSearch()
        }
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
        searchButterflies(query.lowercase()).onEach {
            state = when (it) {
                is Result.Failure -> state.copy(filteredButterflies = flowOf(), searchText = query)
                Result.Loading -> state.copy(filteredButterflies = flowOf(), searchText = query)
                is Result.Success -> state.copy(filteredButterflies = it.value, searchText = query)
            }
        }.launchIn(viewModelScope)
    }

    private fun resetSearch() {
        state = state.copy(filteredButterflies = state.butterflies, searchText = "")
    }
}