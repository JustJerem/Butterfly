package com.jeremieguillot.butterfly.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeremieguillot.butterfly.domain.interactors.GetConfusionButterflies
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.jeremieguillot.butterfly.presentation.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getConfusionButterflies: GetConfusionButterflies,
) : ViewModel() {

    var confussionButterflies by mutableStateOf(listOf<ButterflyModel>())

    //TODO this method is working fine, but when calling it from the view,
    // it's on repeat... peut etre parce que la vue se dessine en boucle
    fun checkConfussionButterflies(selectedButterfly: ButterflyModel) {
        getConfusionButterflies(selectedButterfly).onEach {
            when (it) {
                is Result.Failure -> confussionButterflies = emptyList()
                Result.Loading -> confussionButterflies = emptyList()
                is Result.Success -> confussionButterflies = it.value
            }
        }.launchIn(viewModelScope)
    }
}