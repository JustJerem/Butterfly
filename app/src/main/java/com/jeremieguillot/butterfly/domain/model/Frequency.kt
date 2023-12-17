package com.jeremieguillot.butterfly.domain.model

enum class Frequency(override val displayName: String) : Displayable {
    COMMUN("Commun"),
    PEU_COMMUN("Peu commun"),
    RARE("Rare")
}
