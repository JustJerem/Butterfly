package com.jeremieguillot.butterfly.domain.model

import androidx.annotation.StringRes
import com.jeremieguillot.butterfly.R

enum class MonthEnum(val fullName: String, @StringRes val shortName: Int) {
    JANUARY("Janvier", R.string.january_shortname),
    FEBRUARY("Février", R.string.february_shortname),
    MARCH("Mars", R.string.march_shortname),
    APRIL("Avril", R.string.april_shortname),
    MAY("Mai", R.string.may_shortname),
    JUNE("Juin", R.string.june_shortname),
    JULY("Juillet", R.string.july_shortname),
    AUGUST("Août", R.string.august_shortname),
    SEPTEMBER("Septembre", R.string.september_shortname),
    OCTOBER("Octobre", R.string.october_shortname),
    NOVEMBER("Novembre", R.string.november_shortname),
    DECEMBER("Décembre", R.string.december_shortname);
}