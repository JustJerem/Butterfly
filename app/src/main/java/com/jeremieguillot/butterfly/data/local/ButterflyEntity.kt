package com.jeremieguillot.butterfly.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.jeremieguillot.butterfly.domain.model.ConservationStatus
import com.jeremieguillot.butterfly.domain.model.VisibleMonth

@Entity(tableName = "butterfly")
data class ButterflyEntity(
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
    @PrimaryKey val id: String,
    val illustration: List<String>,
    val latinName: String,
    val map: String,
    val maxAltitude: Int,
    val maxWingspan: Int,
    val minAltitude: Int,
    val minWingspan: Int,
    val naturalHabitats: List<String>,
    val confusionButterfliesId: List<String>,
    val photoAuthor: String,
    val photos: List<String>,
    val possibleConfusions: String,
    val protectionStatus: String,
    val updated: String,
    val winteringStage: List<String>,
    val carousel: List<String>
) {
    fun toDomainModel(): ButterflyModel {
        return ButterflyModel(
            collectionId = collectionId,
            collectionName = collectionName,
            commonName = commonName,
            conservationStatusFrance = conservationStatusFrance,
            created = created,
            family = family,
            flightPeriod = flightPeriod,
            frequency = frequency,
            generationsPerYear = generationsPerYear,
            hostPlants = hostPlants,
            id = id,
            illustration = illustration,
            latinName = latinName,
            map = map,
            maxAltitude = maxAltitude,
            maxWingspan = maxWingspan,
            minAltitude = minAltitude,
            minWingspan = minWingspan,
            naturalHabitats = naturalHabitats,
            confusionButterfliesId = confusionButterfliesId,
            photoAuthor = photoAuthor,
            photos = photos,
            possibleConfusions = possibleConfusions,
            protectionStatus = protectionStatus,
            updated = updated,
            winteringStage = winteringStage,
            carousel = carousel,
        )
    }
}
