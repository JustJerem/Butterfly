@file:OptIn(InternalCoroutinesApi::class)

package com.jeremieguillot.butterfly.data.repository

import com.jeremieguillot.butterfly.data.network.client.ApiClient
import com.jeremieguillot.butterfly.domain.interactors.common.Failure
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.jeremieguillot.butterfly.domain.repository.ButterflyRepository
import com.jeremieguillot.olympicgame.data.network.util.NetworkHandler
import kotlinx.coroutines.InternalCoroutinesApi

class ButterflyRepositoryImpl(
    private val networkHandler: NetworkHandler,
    private val apiClient: ApiClient
) : ButterflyRepository {

    //Get All Butterflies from the RemoteData API
    override suspend fun getButterflies(): List<ButterflyModel> {
        if (networkHandler.isNetworkAvailable()) {
            try {
                val response = apiClient.getButterflies()
                return response.map { it.toDomainModel() }
            } catch (e: Exception) {
                throw Failure.ServerError(e.message)
            }
        } else throw Failure.NetworkConnection
    }
}