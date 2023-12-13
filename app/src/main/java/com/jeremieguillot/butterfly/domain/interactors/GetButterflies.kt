package com.jeremieguillot.butterfly.domain.interactors

import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.jeremieguillot.butterfly.domain.repository.ButterflyRepository
import com.jeremieguillot.butterfly.presentation.data.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetButterflies @Inject constructor(
    private val butterflyRepository: ButterflyRepository
) {

    operator fun invoke(): Flow<Result<List<ButterflyModel>>> = flow {
        emit(Result.Loading)

        val butterflies = butterflyRepository.getAllButterflies()

        emit(Result.Success(butterflies))
    }.catch { error ->
        Timber.e(error)
        emit(Result.Failure(error.message, error))
    }
}