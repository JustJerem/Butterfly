package com.jeremieguillot.butterfly.domain.interactors

import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.jeremieguillot.butterfly.domain.repository.ButterflyManager
import com.jeremieguillot.butterfly.presentation.data.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

/*
* Sometimes butterflies have similarities that strongly resemble them, so this , method returns the objects.
* */
class GetConfusionButterflies @Inject constructor(
    private val butterflyManager: ButterflyManager
) {
    operator fun invoke(selectedButterfly: ButterflyModel): Flow<Result<List<ButterflyModel>>> =
        flow {
            emit(Result.Loading)

            val butterflies =
                selectedButterfly.confusionButterfliesId.map { butterflyManager.getButterfly(it) }

            emit(Result.Success(butterflies))
        }.catch { error ->
            Timber.e(error)
            emit(Result.Failure(error.message, error))
        }
}