package com.jeremieguillot.butterfly.domain.model

import com.jeremieguillot.butterfly.presentation.utils.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class ButterflyModel(
    val collectionId: String,
    val collectionName: String,
    val commonName: String,
    val conservationStatusFrance: ConservationStatus,
    val created: String,
    val family: String,
    val flightPeriod: List<VisibleMonth>,
    val frequency: String,
    val generationsPerYear: Int,
    val hostPlants: List<String>,
    val id: String,
    val latinName: String,
    val maxAltitude: Int,
    val maxWingspan: Int,
    val minAltitude: Int,
    val minWingspan: Int,
    val naturalHabitats: List<String>,
    val confusionButterfliesId: List<String>,
    val possibleConfusions: String,
    val protectionStatus: String,
    val updated: String,
    val winteringStage: List<String>,
    val carousel: List<ImageInfo>,
    val thumbnail: ImageInfo
)

enum class ImageCategory {
    PHOTO, ILLUSTRATION, MAP
}

@Serializable
data class ImageInfo(
    val category: ImageCategory,
    val filePath: String,
    val captureDate: @Serializable(with = DateSerializer::class) Date? = null,
    val authorName: String? = null,
    val contentDescription: String? = null,
)