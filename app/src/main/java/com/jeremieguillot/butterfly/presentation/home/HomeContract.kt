package com.jeremieguillot.butterfly.presentation.home

import androidx.paging.PagingData
import com.jeremieguillot.butterfly.R
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.jeremieguillot.butterfly.presentation.filter.CategoryType
import com.jeremieguillot.butterfly.presentation.home.composable.ButterflyCategory
import com.jeremieguillot.butterfly.presentation.utils.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class HomeContract {

    sealed class Event {
        data class SearchButterflies(val query: String) : Event()
        data class ButterflyCategorySelected(val family: ButterflyCategory) : Event()

        //Filter
        data class SetSelectedCategory(val category: CategoryType) : Event()

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
        val family: ButterflyCategory = ButterflyCategory.PAPILIONIDES,
        val isSearchBarVisible: Boolean = false,
        val butterflies: Flow<PagingData<ButterflyModel>> = flowOf(),
        val filteredButterflies: Flow<PagingData<ButterflyModel>> = flowOf(),

        //Detail
        val confusionButterflies: List<ButterflyModel> = emptyList(),
    )
}