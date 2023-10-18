package com.jeremieguillot.butterfly.domain.model

import androidx.annotation.StringRes
import kotlinx.serialization.Serializable

@Serializable
data class VisibleMonth(@StringRes val nameRes: Int, val isValidated: Boolean)