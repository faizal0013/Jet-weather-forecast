package com.demo.jetweatherforecast.navigations

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.demo.jetweatherforecast.screens.main.MainViewModel
import com.demo.jetweatherforecast.screens.about.AboutScreenScreen
import com.demo.jetweatherforecast.screens.favorite.FavoriteScreen
import com.demo.jetweatherforecast.screens.main.MainScreen
import com.demo.jetweatherforecast.screens.search.SearchScreen
import com.demo.jetweatherforecast.screens.setting.SettingsScreen
import com.demo.jetweatherforecast.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name){
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        composable(
            "${WeatherScreens.MainScreen.name}/{city}",
            arguments = listOf(
                navArgument("city") {
                    type = NavType.StringType
                }
            )
        ) {navBack  ->
            val mainViewModel = hiltViewModel<MainViewModel>()

            navBack.arguments?.getString("city")?.let { cityName ->
                MainScreen(
                    navController = navController,
                    mainViewModel = mainViewModel,
                    cityName = cityName
                )
            }
        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name) {
            AboutScreenScreen(navController = navController)
        }

        composable(WeatherScreens.FavoriteScreen.name) {
            FavoriteScreen(navController = navController)
        }

        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }
    }

}

