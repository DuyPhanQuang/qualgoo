package wangyeo.interview.qualgoo.routes

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import wangyeo.interview.feature.common.R

enum class DrawerTab(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
) {
    HOME(
        route = Screen.Home.route,
        title = R.string.current_weather_tab,
        icon = R.drawable.ic_cloud,
    ),
    SEVEN_DAYS_WEATHER(
        route = Screen.SevenDaysWeather.route,
        title = R.string.seven_days_weather_tab,
        icon = R.drawable.ic_calendar,
    ),
}

enum class NestedGraph(val route: String) {
    HOME(route = "home_nav"), SPLASH(route = "splash_nav"),
}