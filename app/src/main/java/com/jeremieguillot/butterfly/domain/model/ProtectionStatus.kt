package com.jeremieguillot.butterfly.domain.model

enum class ProtectionStatus(override val displayName: String) : Displayable {
    PAS_PROTÉGÉ("Pas protégé"),
    PROTÉGÉ_LOCATEMENT("Protégé localement"),
    PROTÉGÉ_NATIONALEMENT("Protégé nationalement")
}
