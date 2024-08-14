package wangyeo.interview.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NavigationDrawerLabel(
    modifier: Modifier = Modifier,
    colors: NavigationDrawerItemColors = NavigationDrawerItemDefaults.colors(),
    icon: (@Composable () -> Unit)? = null,
    label: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier
            .height(56.0.dp)
            .fillMaxWidth(),
        color = colors.containerColor(false).value,
    ) {
        Row(
            modifier = Modifier.padding(start = 16.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                val iconColor = colors.iconColor(false).value
                CompositionLocalProvider(LocalContentColor provides iconColor, content = icon)
                Spacer(Modifier.width(12.dp))
            }

            Box(Modifier.weight(1f)) {
                val labelColor = colors.textColor(selected = false).value
                CompositionLocalProvider(LocalContentColor provides labelColor, content = label)
            }
        }
    }
}
