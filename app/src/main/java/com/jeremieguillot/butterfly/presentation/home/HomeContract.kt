package com.jeremieguillot.butterfly.presentation.home

import com.jeremieguillot.butterfly.R
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.jeremieguillot.butterfly.presentation.utils.UiText

class HomeContract {

    sealed class Event {
        object RequestButterflies : Event()
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
        val butterflies: List<ButterflyModel> = emptyList(),
        val filteredButterflies: List<ButterflyModel> = emptyList(),

        //Detail
        val selectedIndexButterfly: Int = 0,
        val confusionButterflies: List<ButterflyModel> = emptyList(),
    )
}