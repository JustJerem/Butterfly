//package com.jeremieguillot.butterfly.data.local
//
//import com.jeremieguillot.butterfly.domain.model.ButterflyModel
//import com.jeremieguillot.butterfly.domain.repository.ButterflyManager
//
//class ButterflyManagerImpl : ButterflyManager {
//    private val butterflies = mutableListOf<ButterflyModel>()
//
//    override fun getButterflies() = butterflies
//
//    override fun setButterflies(currentButterflies: List<ButterflyModel>) {
//        butterflies.clear()
//        butterflies.addAll(currentButterflies)
//    }
//
//    override fun getButterfly(butterflyId: String): ButterflyModel {
//        return butterflies.first { it.id == butterflyId }
//    }
//}