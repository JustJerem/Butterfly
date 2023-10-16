package com.jeremieguillot.butterfly.data.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CallResponse(
    @Json(name = "items")
    val items: List<ButterflyResponse>,
    @Json(name = "page")
    val page: Int,
    @Json(name = "perPage")
    val perPage: Int,
    @Json(name = "totalItems")
    val totalItems: Int,
    @Json(name = "totalPages")
    val totalPages: Int
)