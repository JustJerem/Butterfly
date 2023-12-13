package com.jeremieguillot.butterfly.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class ButterflyModel(
    val collectionId: String,
    val collectionName: String,
    val created: String,
    val updated: String,
    val id: String,
    val commonName: String,
    val latinName: String,
    val conservationStatusFrance: ConservationStatus,
    val protectionStatus: String,
    val family: String,
    val thumbnail: ImageInfo,
    val mapDate: @Contextual Date,
//    val photos: List<String>,
    val carousel: List<ImageInfo>,
    val flightPeriod: List<VisibleMonth>,
    val frequency: String,
    val generationsPerYear: Int,
    val naturalHabitats: List<String>,
    val hostPlants: List<String>,
    val winteringStage: List<String>,
    val maxAltitude: Int,
    val minAltitude: Int,
    val maxWingspan: Int,
    val minWingspan: Int,
    val confusionButterfliesId: List<String>,
    val possibleConfusions: String
)

enum class ImageCategory {
    PHOTO, ILLUSTRATION, MAP
}

@Serializable
data class ImageInfo(
    val category: ImageCategory,
    val filePath: String,
    val captureDate: @Contextual Date? = null,
    val authorName: String? = null,
    val contentDescription: String? = null,
)
