package com.demo.jetweatherforecast.di

import android.content.Context
import androidx.room.Room
import com.demo.jetweatherforecast.dao.FavoriteDao
import com.demo.jetweatherforecast.database.WeatherDatabase
import com.demo.jetweatherforecast.network.WeatherAPI
import com.demo.jetweatherforecast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.annotation.Signed

@Module
@InstallIn(SingletonComponent::class)
object AppModel {

    @Signed
    @Provides
    fun provideOpenWeatherAPI(): WeatherAPI {
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }

    @Signed
    @Provides
    fun provideFavoriteDao(weatherDatabase: WeatherDatabase): FavoriteDao
            = weatherDatabase.favoriteDao()

    @Signed
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase
            = Room.databaseBuilder(
                    context,
                    WeatherDatabase::class.java,
                    "weather_database"
            )
                .fallbackToDestructiveMigrationOnDowngrade()
                .build()
}