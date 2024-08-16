package wangyeo.interview.feature.home.extensions

import wangyeo.interview.feature.common.R

enum class WeatherType(val weather: String) {
    FEW_CLOUDS("02"),
    SCATTERED_CLOUDS("03"),
    BROKEN_CLOUDS("04"),
    SHOWER_RAIN("09"),
    RAIN("10"),
    THUNDERSTORM("11"),
    SNOW("13"),
    MIST("50"),
}


fun String.toBackground(): Int = when {
    contains(WeatherType.FEW_CLOUDS.weather) -> {
        R.drawable.bg_few_clouds
    }
    contains(WeatherType.SCATTERED_CLOUDS.weather) -> {
        R.drawable.bg_scattered_clouds
    }
    contains(WeatherType.BROKEN_CLOUDS.weather) -> {
        R.drawable.bg_broken_clouds
    }
    contains(WeatherType.SHOWER_RAIN.weather) -> {
        R.drawable.bg_shower_rain
    }
    contains(WeatherType.RAIN.weather) -> {
        R.drawable.bg_rain
    }
    contains(WeatherType.THUNDERSTORM.weather) -> {
        R.drawable.bg_thunderstorm
    }
    contains(WeatherType.MIST.weather) -> {
        R.drawable.bg_mist
    }
    contains(WeatherType.SNOW.weather) -> {
        R.drawable.bg_snow
    }
    else -> {
        R.drawable.bg_clear_sky
    }
}

fun String.toIcon(): Int = when {
    contains(WeatherType.FEW_CLOUDS.weather) -> {
        R.drawable.ic_few_clouds
    }
    contains(WeatherType.SCATTERED_CLOUDS.weather) -> {
        R.drawable.ic_scattered_clouds
    }
    contains(WeatherType.BROKEN_CLOUDS.weather) -> {
        R.drawable.ic_broken_clouds
    }
    contains(WeatherType.SHOWER_RAIN.weather) -> {
        R.drawable.ic_shower_rain
    }
    contains(WeatherType.RAIN.weather) -> {
        R.drawable.ic_rain
    }
    contains(WeatherType.THUNDERSTORM.weather) -> {
        R.drawable.ic_thunderstorm
    }
    contains(WeatherType.MIST.weather) -> {
        R.drawable.ic_mist
    }
    contains(WeatherType.SNOW.weather) -> {
        R.drawable.ic_snow
    }
    else -> {
        R.drawable.ic_clear_sky
    }
}