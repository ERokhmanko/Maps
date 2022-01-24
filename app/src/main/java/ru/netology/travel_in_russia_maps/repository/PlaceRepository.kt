package ru.netology.travel_in_russia_maps.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.travel_in_russia_maps.dto.Place


interface PlaceRepository {
    val data: Flow<List<Place>>

    suspend fun removeById(id: Long)
    suspend fun save(place: Place)
    suspend fun getAll()
    suspend fun update(place: Place)
}