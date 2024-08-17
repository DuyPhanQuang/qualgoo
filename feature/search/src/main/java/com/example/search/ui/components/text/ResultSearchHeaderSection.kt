package com.example.search.ui.components.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultSearchHeaderSection(
    modifier: Modifier = Modifier,
    textTitle: String,
    textAction: String = "",
    onClickAction: () -> Unit = {},
) {
    Row(
        modifier = modifier.height(60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = textTitle,
            style = MaterialTheme.typography.titleLarge,
        )

        if (textAction.isNotBlank()) {
            TextButton(onClick = onClickAction) {
                Text(text = textAction)
            }
        }
    }
}