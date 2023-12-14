package com.jeremieguillot.butterfly.domain.interactors

import androidx.paging.PagingData
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

    operator fun invoke(
        family: String? = null,
        name: String? = null
    ): Flow<Result<Flow<PagingData<ButterflyModel>>>> = flow {
        emit(Result.Loading)

       val params = if (family !=null){
           "(family=\"$family\")"
       } else if (name != null){
           "(common_name~\"$name\" || latin_name~\"$name\")"
       } else ""

//https://warm-butterfly.pockethost.io/api/collections/butterfly/records?filter=(common_name~"tor" || latin_name~"tor")

        val butterflies = butterflyRepository.getAllButterflies(params)

        emit(Result.Success(butterflies.flow))

    }.catch { error ->
        Timber.e(error)
        emit(Result.Failure(error.message, error))
    }
}