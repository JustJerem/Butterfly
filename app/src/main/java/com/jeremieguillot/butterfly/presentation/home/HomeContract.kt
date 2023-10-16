package com.jeremieguillot.butterfly.presentation.home

import com.jeremieguillot.butterfly.R
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.jeremieguillot.butterfly.presentation.utils.UiText

class HomeContract {

    sealed class Event {
        object RequestButterflies : Event()
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
        val butterflies: List<ButterflyModel> = emptyList()
    )
}