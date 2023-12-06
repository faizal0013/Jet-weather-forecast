package com.demo.jetweatherforecast.screens.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.jetweatherforecast.models.Favorite
import com.demo.jetweatherforecast.repository.WeatherDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject  constructor(
    private val weatherDatabaseRepository: WeatherDatabaseRepository
) : ViewModel() {
     private val _favList =  MutableStateFlow<List<Favorite>>(emptyList())

    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDatabaseRepository
                .getFavorite()
                .distinctUntilChanged()
                .collect { listOfFav ->
                    if(listOfFav.isNotEmpty()) {
                        _favList.value = listOfFav
                        Log.d("listOfFav", listOfFav.toString())
                    } else {
                        _favList.value = emptyList()
                    }
                }
        }
    }

    fun insertFavorite(favorite: Favorite) = viewModelScope.launch {
            weatherDatabaseRepository
                .insertFavorite(favorite)
    }

    fun updateFavorite(favorite: Favorite) = viewModelScope.launch {
        weatherDatabaseRepository.updateFavorite(favorite)
    }

    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch {
        weatherDatabaseRepository.deleteFavorite(favorite)
    }

    fun getFavoriteByCity(city: String) = viewModelScope.launch {
        weatherDatabaseRepository.getFavoriteByCity(city)
    }
}