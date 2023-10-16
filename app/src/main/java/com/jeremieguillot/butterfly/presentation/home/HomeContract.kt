package com.jeremieguillot.butterfly.presentation.home

import com.jeremieguillot.butterfly.R
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.jeremieguillot.butterfly.presentation.utils.UiText

class HomeContract {

    sealed class Event {
        object RequestButterflies : Event()
        data class SearchButterflies(val query: String) : Event()
        object ResetSearch : Event()
        object ToggleSearchBar : Event()
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
        val butterflies: List<ButterflyModel> = emptyList(),
        val filteredButterflies: List<ButterflyModel> = emptyList()
    )
}