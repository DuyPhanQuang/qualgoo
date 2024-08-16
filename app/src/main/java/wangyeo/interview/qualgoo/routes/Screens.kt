package wangyeo.interview.qualgoo.routes

sealed class Screen(val route: String) {
    object Splash : Screen("splash")

    object Home : Screen("home")

    object SevenDaysWeather : Screen("seven_days_weather")

    object SearchByMap : Screen("search_by_map")

    object SearchByText : Screen("search_by_text")
}
