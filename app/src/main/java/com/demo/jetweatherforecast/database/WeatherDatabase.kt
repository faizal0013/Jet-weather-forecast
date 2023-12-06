package com.demo.jetweatherforecast.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.jetweatherforecast.dao.FavoriteDao
import com.demo.jetweatherforecast.models.Favorite
import com.demo.jetweatherforecast.models.Unit

@Database(
    entities = [
        Favorite::class,
        Unit::class,
    ],
    version = 2,
    exportSchema = false
)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

}