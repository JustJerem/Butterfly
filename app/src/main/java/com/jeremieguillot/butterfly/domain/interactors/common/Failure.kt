package com.jeremieguillot.butterfly.domain.interactors.common

sealed class Failure : Exception() {
    object NetworkConnection : Failure()
    object AccessTokenInvalid : Failure()
    object InvalidObject : Failure()
    data class ServerError(override val message: String?) : Failure()
    data class UnauthorizedError(override val message: String?) : Failure()
    data class ServerUnderMaintenance(override val message: String?) : Failure()
    data class DataError(override val message: String?) : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(open val stringId: Int? = null) : Failure()
}