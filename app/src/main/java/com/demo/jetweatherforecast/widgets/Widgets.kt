package com.demo.jetweatherforecast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.demo.jetweatherforecast.R
import com.demo.jetweatherforecast.models.City
import com.demo.jetweatherforecast.models.WeatherItem
import com.demo.jetweatherforecast.utils.formatDate
import com.demo.jetweatherforecast.utils.formatDateTime
import com.demo.jetweatherforecast.utils.formatDecimals

@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageURL = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"

    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatDate(weather.dt).split(",").first(),
                modifier = Modifier
                    .padding(start = 5.dp),
            )

            WeatherStateImage(image = imageURL)

            Surface(
                shape = CircleShape,
                color = Color(0XFFFFC400)
            ) {
                Text(
                    text = weather.weather.first().description,
                    modifier = Modifier
                        .padding(4.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        color = Color.Blue.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                    ){
                        append(formatDecimals(weather.main.temp_max) + "°")
                    }

                    withStyle(style = SpanStyle(
                        color = Color.LightGray
                    )
                    ){
                        append(formatDecimals(weather.main.temp_min) + "°")
                    }
                }
            )
        }
    }


}

@Composable
fun SunsetRunRiseRow(weather: City) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
        ){
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise",
                modifier = Modifier.size(20.dp)
            )

            Text(text = formatDateTime(weather.sunrise), style = MaterialTheme.typography.titleMedium)
        }

        Row(
            modifier = Modifier
                .padding(4.dp)
        ){
            Text(text = formatDateTime(weather.sunset), style = MaterialTheme.typography.titleMedium)

            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunset",
                modifier = Modifier.size(20.dp)
            )

        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity",
                modifier = Modifier.size(20.dp)
            )

            Text(text = "${weather.main.humidity}%", style = MaterialTheme.typography.titleMedium)
        }

        Row(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure",
                modifier = Modifier.size(20.dp)
            )

            Text(text = "${weather.main.pressure} psi", style = MaterialTheme.typography.titleMedium)
        }

        Row(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind",
                modifier = Modifier.size(20.dp)
            )

            Text(text = "${weather.wind.deg} " + if (isImperial) "mph" else "m/s", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun WeatherStateImage(image: String) {
    Image(
        painter = rememberAsyncImagePainter(model = image),
        contentDescription = "icon image",
        modifier = Modifier
            .size(80.dp)
    )
}
