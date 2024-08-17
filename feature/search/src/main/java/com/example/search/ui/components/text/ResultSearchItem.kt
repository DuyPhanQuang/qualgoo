package com.example.search.ui.components.text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.android.libraries.places.api.model.AutocompletePrediction

@Composable
fun ResultSearchItem(
    modifier: Modifier = Modifier,
    item: AutocompletePrediction,
    onClickResultSearch: (AutocompletePrediction) -> Unit = {},
) {
    Column(
        modifier = modifier
            .height(66.dp)
            .clickable {
                onClickResultSearch.invoke(item)
            },
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = item.getPrimaryText(null).toString(),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 10.dp),
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = item.getSecondaryText(null).toString(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}
