package com.jeremieguillot.butterfly.data.network.client

import com.jeremieguillot.butterfly.data.network.response.CallResponse
import retrofit2.http.GET

interface ApiClient {

    @GET("collections/butterfly/records")
    suspend fun getButterflies(): CallResponse
}