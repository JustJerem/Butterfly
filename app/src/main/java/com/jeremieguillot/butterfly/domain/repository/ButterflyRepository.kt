package com.jeremieguillot.butterfly.domain.repository

import com.jeremieguillot.butterfly.domain.model.ButterflyModel

interface ButterflyRepository {
    suspend fun getButterflies(): List<ButterflyModel>
}