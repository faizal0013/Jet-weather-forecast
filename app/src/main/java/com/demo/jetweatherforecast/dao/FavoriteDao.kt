package com.demo.jetweatherforecast.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.demo.jetweatherforecast.models.Favorite
import com.demo.jetweatherforecast.models.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM Favorite")
    fun getFavorite(): Flow<List<Favorite>>

    @Query("SELECT * FROM Favorite where city = :city")
    suspend fun getFavoriteByCity(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Delete()
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * FROM Unit")
    fun getUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Query("DELETE FROM Unit")
    suspend fun deleteUnits()

    @Delete
    suspend fun deleteUnit(unit: Unit)
}