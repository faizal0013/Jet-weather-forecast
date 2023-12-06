package com.demo.jetweatherforecast.widgets

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.demo.jetweatherforecast.models.Favorite
import com.demo.jetweatherforecast.navigations.WeatherScreens
import com.demo.jetweatherforecast.screens.favorite.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String,
    icon: ImageVector? = null,
    isMainScreen: Boolean = false,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
    ) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    
    if(showDialog.value){
        ShowDialogDropDownMenu(showDialog = showDialog, navController = navController)
    }


    CenterAlignedTopAppBar(
        modifier = Modifier.shadow(
            elevation = elevation
        ),
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.secondary,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                ),
            )
        },
        actions = {
                  if(isMainScreen){
                      IconButton(
                          onClick = onAddActionClicked
                      ) {
                          Icon(
                              imageVector = Icons.Default.Search,
                              contentDescription = "Search Icon",
                          )
                      }
                      IconButton(
                          onClick = {
                              showDialog.value = !showDialog.value
                          }
                      ) {
                          Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "MoreVert Icon")
                      }
                  }else{
                      Box {}
                  }
        },
        navigationIcon = {

            if(isMainScreen){

                val city = title.split(",").first().trim()
                val country = title.split(",").last().trim()

                val showIt = remember {
                    mutableStateOf(false)
                }

                val context = LocalContext.current

                val isAlreadyFavList = favoriteViewModel
                    .favList
                    .collectAsState()
                    .value
                    .filter { item ->
                        (item.city == city)
                    }

                Log.d("isAlreadyFavList", isAlreadyFavList.toString())

                if(isAlreadyFavList.isNullOrEmpty()){
                    IconButton(
                        onClick = {
                            favoriteViewModel
                                .insertFavorite(
                                    Favorite(
                                        city = city,
                                        country = country
                                    )
                                ).run {
                                    showIt.value = true
                                }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite Icon",
                            modifier = Modifier
                                .scale(0.9f),
                            tint = Color.Red.copy(alpha = 0.6f)
                        )
                    }
                } else {
                    Box {}
                    showIt.value = false
                }

                ShowToast(context = context, showIt)
            }

            if(icon != null){
                Icon(
                    imageVector = icon,
                    contentDescription = "navigationIcon",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.clickable {
                        onButtonClicked()
                    }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
    )
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value){
        Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ShowDialogDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {

    var expanded by remember {
        mutableStateOf(true)
    }

    val items = listOf(
        mapOf(
            "icon" to Icons.Default.Info,
            "title" to "About",
            "navigate" to WeatherScreens.AboutScreen.name
        ),
        mapOf(
            "icon" to Icons.Default.FavoriteBorder,
            "title" to "Favorites",
            "navigate" to WeatherScreens.FavoriteScreen.name
        ),
        mapOf(
            "icon" to Icons.Default.Settings,
            "title" to "Settings",
            "navigate" to WeatherScreens.SettingsScreen.name
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp),
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                showDialog.value = false
            },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White),
        ) {
            items.forEachIndexed { _, s ->
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(imageVector = s["icon"] as ImageVector, contentDescription = "icon")
                    },
                    text = {
                           Text(
                               text = s["title"] as String,
                               modifier = Modifier
                                   .fillMaxWidth()
                           )
                    },
                    onClick = {
                        expanded = false
                        showDialog.value = false

                        navController.navigate(s["navigate"] as String)
                    }
                )
            }
        }
    }

}
