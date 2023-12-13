package com.jeremieguillot.butterfly.domain.interactors

import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.jeremieguillot.butterfly.presentation.data.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetButterfly @Inject constructor(
//    private val butterflyManagerImp: ButterflyManager
) {

    operator fun invoke(id: String): Flow<Result<ButterflyModel?>> = flow {
        emit(Result.Loading)

//        val butterflies = butterflyManagerImp.getButterfly(id)

        emit(Result.Success(null))
    }.catch { error ->
        Timber.e(error)
        emit(Result.Failure(error.message, error))
    }
}