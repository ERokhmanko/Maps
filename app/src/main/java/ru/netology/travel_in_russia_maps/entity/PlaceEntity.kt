package ru.netology.travel_in_russia_maps.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.travel_in_russia_maps.dto.Place

@Entity
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val avatarPlace: String,
    val name: String,
    val description: String,
    val latitude: Float,
    val longitude: Float

) {
    fun toDto() =
        Place(
            id, avatarPlace, name, description, latitude, longitude
        )

    companion object {
        fun fromDto(place: Place) =
            PlaceEntity(
                place.id,
                place.avatarPlace,
                place.name,
                place.description,
                place.latitude,
                place.longitude,
            )
    }

}

fun List<PlaceEntity>.toDto() = map(PlaceEntity::toDto)
fun List<Place>.toEntity() = map(PlaceEntity::fromDto)