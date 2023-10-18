package com.jeremieguillot.butterfly.domain.repository

import com.jeremieguillot.butterfly.domain.model.ButterflyModel

interface ButterflyManager {
    fun getButterflies(): MutableList<ButterflyModel>
    fun setButterflies(currentButterflies: List<ButterflyModel>)
    fun getButterfly(butterflyId: String): ButterflyModel
}