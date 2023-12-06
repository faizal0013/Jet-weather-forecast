package com.demo.jetweatherforecast.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.demo.jetweatherforecast.R
import com.demo.jetweatherforecast.widgets.WeatherAppBar

@Composable
fun AboutScreenScreen(navController: NavController) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "About",
                navController = navController,
                icon = Icons.Default.ArrowBack
            ) {
                navController.popBackStack()
            }
        }
    ) { paddingValues: PaddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.about_app),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = "Weather App by:https://openweathermap.org/",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Light
                )
            }
        }

    }
}