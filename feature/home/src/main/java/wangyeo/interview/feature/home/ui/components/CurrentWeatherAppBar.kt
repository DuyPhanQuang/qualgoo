package wangyeo.interview.feature.home.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.sharp.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import wangyeo.interview.feature.common.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentWeatherAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    city: String? = null,
    onDrawer: () -> Unit = {},
    onNavigateSearch: () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = {
            if (title.isNotBlank()) {
                Text(text = title, maxLines = 1, overflow = TextOverflow.Visible)
            }
        },
        navigationIcon = {
            IconButton(onClick = onDrawer) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = null,
                )
            }
        },
        actions = {
            Card(
                onClick = onNavigateSearch,
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Sharp.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 5.dp),
                    )

                    Text(
                        text = city ?: stringResource(id = R.string.unknown_address),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        },
    )
}