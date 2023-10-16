package com.jeremieguillot.butterfly.data.network.client

import com.jeremieguillot.butterfly.data.network.response.ButterflyResponse
import retrofit2.http.GET

interface ApiClient {

    @GET("butterfly/records")
    suspend fun getButterflies(): List<ButterflyResponse>
}