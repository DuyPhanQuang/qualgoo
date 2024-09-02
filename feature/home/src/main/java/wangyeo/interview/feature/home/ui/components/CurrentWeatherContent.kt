package wangyeo.interview.feature.home.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import wangyeo.interview.feature.common.R
import wangyeo.interview.feature.home.models.CurrentWeatherViewData
import wangyeo.interview.feature.home.models.HourWeatherViewData

@Composable
fun DetailWeather(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    title: String,
    description: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {
        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = iconId),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 5.dp),
        )

        Text(
            text = description,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Composable
fun Degrees(
    modifier: Modifier = Modifier,
    currentTemp: String,
    currentWeather: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = currentTemp,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.alignBy(LastBaseline),
            )

            Column(modifier = Modifier.alignBy(LastBaseline)) {
                Text(text = "o", modifier = Modifier.padding(bottom = 10.dp))

                Text(text = "c")
            }
        }

        Text(
            text = currentWeather,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 22.sp),
        )
    }
}

@Composable
fun WeatherHour(
    modifier: Modifier = Modifier,
    hour: String,
    @DrawableRes weather: Int,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = hour,
            style = MaterialTheme.typography.titleMedium,
        )

        Image(
            painter = painterResource(id = weather),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(top = 15.dp),
        )
    }
}

@Composable
fun NowWeather(
    modifier: Modifier,
    currentWeather: CurrentWeatherViewData,
) {
    Row(modifier = modifier) {
        Image(
            painter = painterResource(id = currentWeather.background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 50.dp)
                .weight(1f),
            alignment = Alignment.CenterEnd,
            contentScale = ContentScale.FillHeight,
        )

        Column(
            modifier = Modifier
                .padding(end = 15.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(
                    id = R.string.home_text_celsius_high_low,
                    currentWeather.maxTemp,
                    currentWeather.minTemp,
                ),
            )

            Degrees(
                currentWeather = currentWeather.weather,
                currentTemp = currentWeather.temp,
            )

            DetailWeather(
                iconId = R.drawable.ic_sun_rise,
                title = stringResource(id = R.string.sun_rise),
                description = currentWeather.sunRise,
            )

            DetailWeather(
                iconId = R.drawable.ic_wind,
                title = stringResource(id = R.string.wind),
                description = stringResource(id = R.string.home_text_meter_per_second, currentWeather.wind),
            )

            DetailWeather(
                iconId = R.drawable.ic_humidity,
                title = stringResource(id = R.string.humidity),
                description = stringResource(id = R.string.home_text_humidity, currentWeather.humidity),
            )
        }
    }
}

@Composable
fun CurrentWeatherContent(
    currentWeather: CurrentWeatherViewData,
    listHourly: List<HourWeatherViewData>,
    onGoToBook: ()->Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        IconButton(onClick = onGoToBook) {
            Icon(
                imageVector = Icons.Outlined.Menu,
                contentDescription = null,
            )
        }

        NowWeather(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            currentWeather = currentWeather,
        )

        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(
                    top = 50.dp,
                    bottom = 30.dp,
                )
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(
                    count = listHourly.size,
                    key = {
                        listHourly[it].timeStamp
                    },
                ) { index ->
                    WeatherHour(
                        hour = listHourly[index].time,
                        weather = listHourly[index].weatherIcon,
                    )
                }
            }
        }
    }
}