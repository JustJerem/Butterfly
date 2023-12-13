package com.jeremieguillot.butterfly.domain.repository

import com.jeremieguillot.butterfly.domain.model.ButterflyModel

interface ButterflyRepository {
    suspend fun getAllButterflies(): List<ButterflyModel>
    suspend fun getButterflies(ids: List<String>): List<ButterflyModel>
}