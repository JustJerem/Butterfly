package com.jeremieguillot.butterfly.domain.model

data class ButterflyModel(
    val collectionId: String,
    val collectionName: String,
    val commonName: String,
    val conservationStatusFrance: String,
    val created: String,
    val family: String,
    val flightPeriod: List<String>,
    val frequency: String,
    val generationsPerYear: Int,
    val hostPlants: List<String>,
    val id: String,
    val illustration: List<String>,
    val latinName: String,
    val map: String,
    val maxAltitude: Int,
    val maxWingspan: Int,
    val minAltitude: Int,
    val minWingspan: Int,
    val naturalHabitats: List<String>,
    val photoAuthor: String,
    val photos: List<String>,
    val possibleConfusions: String,
    val protectionStatus: String,
    val updated: String,
    val winteringStage: List<String>
)