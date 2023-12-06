package com.demo.jetweatherforecast.repository

import com.demo.jetweatherforecast.dao.FavoriteDao
import com.demo.jetweatherforecast.models.Favorite
import com.demo.jetweatherforecast.models.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDatabaseRepository @Inject constructor(
    private val favoriteDao: FavoriteDao
) {
    fun getFavorite() : Flow<List<Favorite>> = favoriteDao
        .getFavorite()

    suspend fun getFavoriteByCity(city: String): Favorite = favoriteDao
        .getFavoriteByCity(city)

    suspend fun insertFavorite(favorite: Favorite) = favoriteDao
        .insertFavorite(favorite)

    suspend fun updateFavorite(favorite: Favorite) = favoriteDao
        .updateFavorite(favorite)

    suspend fun deleteFavorite(favorite: Favorite)  = favoriteDao
        .deleteFavorite(favorite)

    fun getUnits() : Flow<List<Unit>> = favoriteDao
        .getUnits()

    suspend fun insertUnit(unit: Unit) = favoriteDao
        .insertUnit(unit)

    suspend fun deleteUnits() = favoriteDao
        .deleteUnits()

    suspend fun updateUnit(unit: Unit) = favoriteDao.updateUnit(unit)

    suspend fun deleteUnit(unit: Unit)  = favoriteDao
        .deleteUnit(unit)

}