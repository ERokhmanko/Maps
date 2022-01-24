package ru.netology.travel_in_russia_maps.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.netology.travel_in_russia_maps.entity.PlaceEntity

@Dao
interface PlaceDao {

    @Query("SELECT * FROM PlaceEntity ORDER BY id")
    fun getAll(): Flow<List<PlaceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: PlaceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: List<PlaceEntity>)

    @Query("UPDATE PlaceEntity SET description = :description AND name = :name WHERE id = :id")
    fun update(id: Long, description: String, name: String)

    suspend fun save(place: PlaceEntity) =
        if (place.id == 0L) insert(place) else update(place.id, place.description, place.name)

    @Query("DELETE FROM PlaceEntity WHERE id = :id")
    suspend fun removeById(id: Long)


}
