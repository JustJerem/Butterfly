package com.jeremieguillot.butterfly.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jeremieguillot.butterfly.data.network.client.ApiClient
import com.jeremieguillot.butterfly.data.paging.ButterflyPagingSource
import com.jeremieguillot.butterfly.data.paging.SearchButterflyPagingSource
import com.jeremieguillot.butterfly.domain.interactors.common.Failure
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.jeremieguillot.butterfly.domain.repository.ButterflyManager
import com.jeremieguillot.butterfly.domain.repository.ButterflyRepository
import com.jeremieguillot.olympicgame.data.network.util.NetworkHandler


const val PAGE_SIZE = 30

class ButterflyRepositoryImpl(
    private val networkHandler: NetworkHandler,
    private val apiClient: ApiClient,
    private val butterflyManagerImp: ButterflyManager
) : ButterflyRepository {

    //Get All Butterflies from the RemoteData API
    override suspend fun getButterflies(): Pager<Int, ButterflyModel> {
        if (networkHandler.isNetworkAvailable()) {
            try {
                return Pager(
                    config = PagingConfig(
                        pageSize = PAGE_SIZE,
                    ),
                    pagingSourceFactory = {
                        ButterflyPagingSource(
                            apiClient = apiClient,
                        )
                    }
                )
            } catch (e: Exception) {
                throw Failure.ServerError(e.message)
            }
        } else throw Failure.NetworkConnection
    }

    override suspend fun searchButterflies(query: String): Pager<Int, ButterflyModel> {
        if (networkHandler.isNetworkAvailable()) {
            try {
                return Pager(
                    config = PagingConfig(
                        pageSize = PAGE_SIZE,
                    ),
                    pagingSourceFactory = {
                        SearchButterflyPagingSource(
                            apiClient = apiClient,
                            query = query,
                        )
                    }
                )

            } catch (e: Exception) {
                throw Failure.ServerError(e.message)
            }
        } else throw Failure.NetworkConnection
    }

}