package wangyeo.interview.feature.home.models.factory

import wangyeo.interview.feature.home.models.CurrentWeatherViewData
import wangyeo.interview.feature.common.R

fun previewCurrentWeatherViewData() = CurrentWeatherViewData(
    city = "Ha Noi",
    maxTemp = "40",
    minTemp = "30",
    temp = "35",
    weather = "Sunny",
    sunRise = "6:00",
    wind = "10",
    humidity = "70",
    background = R.drawable.bg_clear_sky
)
