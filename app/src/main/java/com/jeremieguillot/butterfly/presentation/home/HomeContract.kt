package com.jeremieguillot.butterfly.presentation.home

import androidx.paging.PagingData
import com.jeremieguillot.butterfly.R
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.jeremieguillot.butterfly.presentation.utils.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class HomeContract {

    sealed class Event {
        data class SearchButterflies(val query: String) : Event()
        data class SetSelectedIndexButterfly(val index: Int) : Event()
        object ToggleSearchBar : Event()
    }

    sealed class Navigation {
        object OpenDetail : Navigation()
    }

    sealed class Error {
        object UnknownIssue : Error() {
            val message: UiText = UiText.StringResource(
                resId = R.string.general_unknown_issue,
            )
        }
    }

    data class State(
        val isViewLoading: Boolean = true,
        val isSearchBarVisible: Boolean = false,
        val searchText: String = "",
        val butterflies: Flow<PagingData<ButterflyModel>> = flowOf(),
        val filteredButterflies: Flow<PagingData<ButterflyModel>> = flowOf(),

        //Detail
        val selectedIndexButterfly: Int = 0,
        val confusionButterflies: List<ButterflyModel> = emptyList(),
    )
}