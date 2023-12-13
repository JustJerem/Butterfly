package com.jeremieguillot.butterfly.data.network.response


import com.jeremieguillot.butterfly.data.network.util.PockethostHelper
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.jeremieguillot.butterfly.domain.model.ConservationStatus
import com.jeremieguillot.butterfly.domain.model.ImageCategory
import com.jeremieguillot.butterfly.domain.model.ImageInfo
import com.jeremieguillot.butterfly.domain.model.MonthEnum
import com.jeremieguillot.butterfly.domain.model.VisibleMonth
import com.jeremieguillot.butterfly.presentation.utils.toDate
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
    @Json(name = "butterfly_confusion")
    val confusionButterfliesId: List<String>,
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
    val winteringStage: List<String>,
    @Json(name = "map_date")
    val mapDate: String,
) {
    fun toDomainModel(): ButterflyModel {
        val illustrationsLink =
            illustration.map { PockethostHelper.getFilePath(collectionId, id, it) }
        val mapLink = PockethostHelper.getFilePath(collectionId, id, map)
        val photosLink = photos.map { PockethostHelper.getFilePath(collectionId, id, it) }
        val status = ConservationStatus.values().first { it.name == conservationStatusFrance }
        val visibleMonth = getVisibilityMonth(flightPeriod)

        val thumbnail = ImageInfo(ImageCategory.PHOTO, photosLink.first(), authorName = photoAuthor)
        val illustration = ImageInfo(ImageCategory.ILLUSTRATION, illustrationsLink.first())
        val map = ImageInfo(ImageCategory.MAP, mapLink, captureDate = mapDate.toDate())
        return ButterflyModel(
            collectionId = collectionId,
            collectionName = collectionName,
            created = created,
            updated = updated,
            id = id,
            commonName = commonName,
            latinName = latinName,
            conservationStatusFrance = status,
            protectionStatus = protectionStatus,
            family = family,
            thumbnail = thumbnail,
//            illustration = illustration,
//            map = mapLink,
            mapDate = mapDate.toDate(),
//            photos = photosLink,
//            photoAuthor = photoAuthor,
            carousel = listOf(thumbnail, illustration, map),
            flightPeriod = visibleMonth,
            frequency = frequency,
            generationsPerYear = generationsPerYear,
            naturalHabitats = naturalHabitats,
            hostPlants = hostPlants,
            winteringStage = winteringStage,
            maxAltitude = maxAltitude,
            minAltitude = minAltitude,
            maxWingspan = maxWingspan,
            minWingspan = minWingspan,
            confusionButterfliesId = confusionButterfliesId,
            possibleConfusions = possibleConfusions
        )
    }

    private fun getVisibilityMonth(flightPeriod: List<String>): List<VisibleMonth> {
        return MonthEnum.values().map {
            VisibleMonth(nameRes = it.shortName, isValidated = flightPeriod.contains(it.fullName))
        }
    }
}