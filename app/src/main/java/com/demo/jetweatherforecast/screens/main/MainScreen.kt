package com.demo.jetweatherforecast.screens.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.demo.jetweatherforecast.data.DataOrException
import com.demo.jetweatherforecast.models.Weather
import com.demo.jetweatherforecast.models.WeatherItem
import com.demo.jetweatherforecast.navigations.WeatherScreens
import com.demo.jetweatherforecast.screens.setting.SettingViewModel
import com.demo.jetweatherforecast.utils.formatDate
import com.demo.jetweatherforecast.utils.formatDecimals
import com.demo.jetweatherforecast.widgets.HumidityWindPressureRow
import com.demo.jetweatherforecast.widgets.SunsetRunRiseRow
import com.demo.jetweatherforecast.widgets.WeatherAppBar
import com.demo.jetweatherforecast.widgets.WeatherDetailRow
import com.demo.jetweatherforecast.widgets.WeatherStateImage

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingViewModel: SettingViewModel = hiltViewModel(),
    cityName: String,
) {

    val curCity: String = cityName.ifBlank { "Vapi" }

    val unitFromDb = settingViewModel.unitList.collectAsState().value

    var unit by remember {
        mutableStateOf("imperial")
    }

    var isImperial by remember {
        mutableStateOf(false)
    }

    if(unit.isNotEmpty()) {

        if(unitFromDb.isNotEmpty()){
            unit = unitFromDb.first().unit.split(" ").first().lowercase()
            Log.d("unit", unit)

            isImperial = unit == "imperial"
        }


        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ){
            value = mainViewModel.getWeatherData(cityName = curCity, unit = unit)
        }.value

        if(weatherData.loading == true){
            CircularProgressIndicator()
        }else if(weatherData.data != null){
            MainScaffold(weather= weatherData.data!!, navController = navController, isImperial = isImperial)
        }
    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController, isImperial: Boolean) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "${weather.city.name} , ${weather.city.country}",
                navController = navController,
                elevation = 5.dp,
                isMainScreen = true,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                }
            )
        }
    ) { paddingValue ->
        Surface(modifier = Modifier.padding(paddingValue)) {
            MainContent(data = weather, isImperial = isImperial)
        }
    }

}

@Composable
fun MainContent(data: Weather, isImperial: Boolean) {
    val imageURL = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"

    Log.d("Image URL", data.list[0].weather.toString())

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(data.list.first().dt),
            style = MaterialTheme
                .typography
                .titleMedium,
            color = MaterialTheme
                .colorScheme
                .secondary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0XFFFFC400)
        ) {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                WeatherStateImage(image = imageURL)

                Text(
                    text = formatDecimals(data.list.first().main.temp) + "°",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = data.list.first().weather.first().main,
                     fontStyle = FontStyle.Italic,
                    )
            }
        }

        HumidityWindPressureRow(weather = data.list.first(), isImperial = isImperial)

        Divider()

        SunsetRunRiseRow(weather = data.city)

        Text(
            text = "This Week",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = Color(0XFFEEF1EF)
                ),
            shape = RoundedCornerShape(size = 14.dp)
        ){


            LazyColumn(
                modifier = Modifier
                    .padding(2.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                items(items = data.list) {item: WeatherItem ->

                    WeatherDetailRow(weather = item)
                }
            }
        }
    }
}
