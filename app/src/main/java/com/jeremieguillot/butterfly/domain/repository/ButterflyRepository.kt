package com.jeremieguillot.butterfly.domain.repository

import androidx.paging.Pager
import com.jeremieguillot.butterfly.domain.model.ButterflyModel

interface ButterflyRepository {
    suspend fun getButterflies(): Pager<Int, ButterflyModel>
    suspend fun searchButterflies(query: String): Pager<Int, ButterflyModel>
}