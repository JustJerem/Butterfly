package com.jeremieguillot.butterfly.domain.repository

import androidx.paging.Pager
import com.jeremieguillot.butterfly.domain.model.ButterflyModel

interface ButterflyRepository {
    suspend fun getAllButterflies(family: String): Pager<Int, ButterflyModel>
    suspend fun getButterflies(ids: List<String>): List<ButterflyModel>
}