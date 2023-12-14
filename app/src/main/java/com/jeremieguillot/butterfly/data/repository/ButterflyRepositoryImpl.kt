package com.jeremieguillot.butterfly.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jeremieguillot.butterfly.data.network.client.ApiClient
import com.jeremieguillot.butterfly.data.paging.ButterflyPagingSource
import com.jeremieguillot.butterfly.domain.interactors.common.Failure
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.jeremieguillot.butterfly.domain.repository.ButterflyRepository
import com.jeremieguillot.olympicgame.data.network.util.NetworkHandler


const val PAGE_SIZE = 30

class ButterflyRepositoryImpl(
    private val networkHandler: NetworkHandler,
    private val apiClient: ApiClient,
) : ButterflyRepository {

    //Get All Butterflies from the RemoteData API
    override suspend fun getAllButterflies(filter: String): Pager<Int, ButterflyModel> {
        if (networkHandler.isNetworkAvailable()) {
            try {

                return Pager(
                    config = PagingConfig(
                        pageSize = PAGE_SIZE,
                    ),
                    pagingSourceFactory = {
                        ButterflyPagingSource(
                            apiClient = apiClient,
                            filter = filter,
                        )
                    }
                )

//                return butterflyManagerImp.getButterflies().ifEmpty {
//                    val response = apiClient.getAllButterflies()
//                    val butterfliesModel = response.items.map { it.toDomainModel() }
//                    butterflyManagerImp.setButterflies(butterfliesModel)
//                    butterfliesModel
//                }
            } catch (e: Exception) {
                throw Failure.ServerError(e.message)
            }
        } else throw Failure.NetworkConnection
    }


    override suspend fun getButterflies(ids: List<String>): List<ButterflyModel> {
        if (networkHandler.isNetworkAvailable()) {
            try {
                val idsFilter = "(${ids.joinToString(" || ") { "id='$it'" }})"
                val response = apiClient.getButterflies(idsFilter)
                val butterfliesModel = response.items.map { it.toDomainModel() }
//                butterflyManagerImp.setButterflies(butterfliesModel)
                return butterfliesModel
            } catch (e: Exception) {
                throw Failure.ServerError(e.message)
            }
        } else throw Failure.NetworkConnection
    }
}