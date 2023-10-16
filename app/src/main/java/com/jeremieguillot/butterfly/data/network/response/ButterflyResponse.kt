package com.jeremieguillot.butterfly.data.network.response


import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ButterflyResponse(
    @Json(name = "collectionId")
    val collectionId: String,
    @Json(name = "collectionName")
    val collectionName: String,
    @Json(name = "common_name")
    val commonName: String,
    @Json(name = "conservation_status_france")
    val conservationStatusFrance: String,
    @Json(name = "created")
    val created: String,
    @Json(name = "family")
    val family: String,
    @Json(name = "flight_period")
    val flightPeriod: List<String>,
    @Json(name = "frequency")
    val frequency: String,
    @Json(name = "generations_per_year")
    val generationsPerYear: Int,
    @Json(name = "host_plants")
    val hostPlants: List<String>,
    @Json(name = "id")
    val id: String,
    @Json(name = "illustration")
    val illustration: List<String>,
    @Json(name = "latin_name")
    val latinName: String,
    @Json(name = "map")
    val map: String,
    @Json(name = "max_altitude")
    val maxAltitude: Int,
    @Json(name = "max_wingspan")
    val maxWingspan: Int,
    @Json(name = "min_altitude")
    val minAltitude: Int,
    @Json(name = "min_wingspan")
    val minWingspan: Int,
    @Json(name = "natural_habitats")
    val naturalHabitats: List<String>,
    @Json(name = "photo_author")
    val photoAuthor: String,
    @Json(name = "photos")
    val photos: List<String>,
    @Json(name = "possible_confusions")
    val possibleConfusions: String,
    @Json(name = "protection_status")
    val protectionStatus: String,
    @Json(name = "updated")
    val updated: String,
    @Json(name = "wintering_stage")
    val winteringStage: List<String>
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
            photoAuthor = photoAuthor,
            photos = photos,
            possibleConfusions = possibleConfusions,
            protectionStatus = protectionStatus,
            updated = updated,
            winteringStage = winteringStage,
        )
    }
}