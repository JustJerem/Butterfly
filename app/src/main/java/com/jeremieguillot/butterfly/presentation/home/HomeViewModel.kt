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

    private fun onEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.RequestButterflies -> requestButterflies()
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
                is Result.Success -> state.copy(isViewLoading = false, butterflies = it.value)
            }
        }.launchIn(viewModelScope)
    }
}