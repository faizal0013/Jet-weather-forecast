package com.demo.jetweatherforecast.screens.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.jetweatherforecast.models.Unit
import com.demo.jetweatherforecast.repository.WeatherDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val weatherDatabaseRepository: WeatherDatabaseRepository
): ViewModel() {
    private val _unitList =  MutableStateFlow<List<Unit>>(emptyList())

    val unitList = _unitList.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDatabaseRepository.getUnits().distinctUntilChanged()
                .collect {listOfUnit ->
                    if(!listOfUnit.isNullOrEmpty()) {
                        _unitList.value = listOfUnit
                    } else {
                        _unitList.value = emptyList()
                    }
                }
        }
    }


    fun insertUnit(unit: Unit) = viewModelScope.launch {
        weatherDatabaseRepository
            .insertUnit(unit)
    }

    fun deleteUnits() = viewModelScope.launch {
        weatherDatabaseRepository.deleteUnits()
    }

    fun updateUnit(unit: Unit) = viewModelScope.launch {
        weatherDatabaseRepository.updateUnit(unit)
    }

    fun deleteUnit(unit: Unit) = viewModelScope.launch {
        weatherDatabaseRepository.deleteUnit(unit)
    }

}