package com.jeremieguillot.butterfly.data.network.client

import com.jeremieguillot.butterfly.data.network.response.CallResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {


    @GET("collections/butterfly/records")
    suspend fun getAllButterflies(
        @Query(value = "page") page: Int = 1,
        @Query("sort") sort: String = "common_name"
    ): CallResponse

    @GET("collections/butterfly/records")
    suspend fun getButterflies(@Query("filter") idsFilter: String): CallResponse
}