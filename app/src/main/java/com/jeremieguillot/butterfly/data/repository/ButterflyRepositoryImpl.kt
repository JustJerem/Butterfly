package com.jeremieguillot.butterfly.data.repository

import com.jeremieguillot.butterfly.data.network.client.ApiClient
import com.jeremieguillot.butterfly.domain.interactors.common.Failure
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.jeremieguillot.butterfly.domain.repository.ButterflyManager
import com.jeremieguillot.butterfly.domain.repository.ButterflyRepository
import com.jeremieguillot.olympicgame.data.network.util.NetworkHandler

class ButterflyRepositoryImpl(
    private val networkHandler: NetworkHandler,
    private val apiClient: ApiClient,
    private val butterflyManagerImp: ButterflyManager
) : ButterflyRepository {

    //Get All Butterflies from the RemoteData API
    override suspend fun getButterflies(): List<ButterflyModel> {
        if (networkHandler.isNetworkAvailable()) {
            try {
                return butterflyManagerImp.getButterflies().ifEmpty {
                    val response = apiClient.getButterflies()
                    val butterfliesModel = response.items.map { it.toDomainModel() }
                    butterflyManagerImp.setButterflies(butterfliesModel)
                    butterfliesModel
                }
            } catch (e: Exception) {
                throw Failure.ServerError(e.message)
            }
        } else throw Failure.NetworkConnection
    }
}