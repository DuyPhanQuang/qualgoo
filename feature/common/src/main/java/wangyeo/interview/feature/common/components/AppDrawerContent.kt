package wangyeo.interview.feature.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import wangyeo.interview.composable.NavigationDrawerLabel
import wangyeo.interview.feature.common.R
import wangyeo.interview.qualgoo.routes.DrawerTab

@Composable
fun ColumnScope.AppDrawerContent(
    selectedItem: wangyeo.interview.qualgoo.routes.DrawerTab,
    onClickCurrentWeather: () -> Unit = {},
    onClickSevenDaysWeather: () -> Unit = {},
) {
    Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))

    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(120.dp),
            painter = painterResource(id = R.drawable.ic_shower_rain),
            contentDescription = null,
        )

        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displaySmall,
        )
    }

    NavigationDrawerLabel {
        Text(text = stringResource(id = R.string.feature_group_tab))
    }

    NavigationDrawerItem(
        icon = {
            Icon(
                painter = painterResource(id = wangyeo.interview.qualgoo.routes.DrawerTab.HOME.icon),
                modifier = Modifier.size(24.dp),
                contentDescription = null,
            )
        },
        label = {
            Text(text = stringResource(id = R.string.current_weather_tab))
        },
        selected = wangyeo.interview.qualgoo.routes.DrawerTab.HOME == selectedItem,
        onClick = onClickCurrentWeather,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
    )

    NavigationDrawerItem(
        icon = {
            Icon(
                painter = painterResource(id = wangyeo.interview.qualgoo.routes.DrawerTab.SEVEN_DAYS_WEATHER.icon),
                modifier = Modifier.size(24.dp),
                contentDescription = null,
            )
        },
        label = {
            Text(text = stringResource(id = R.string.seven_days_weather_tab))
        },
        selected = wangyeo.interview.qualgoo.routes.DrawerTab.SEVEN_DAYS_WEATHER == selectedItem,
        onClick = onClickSevenDaysWeather,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
    )

    NavigationDrawerLabel {
        Text(text = stringResource(id = R.string.another_group_tab))
    }


    Spacer(modifier = Modifier.weight(1f))

    NavigationDrawerLabel {
        Text(text = "Build version: 1.0.0")
    }

    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
}
