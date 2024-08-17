package com.example.search.ui.components.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import wangyeo.interview.composable.InfinityText
import wangyeo.interview.feature.common.extensions.clearFocusOnKeyboardDismiss

@Composable
fun SearchByTextAppBar(
    modifier: Modifier = Modifier,
    placeholder: List<String> = emptyList(),
    text: String = "",
    onTextChange: (String) -> Unit = {},
    onClickBack: () -> Unit = {},
    onClickMap: () -> Unit = {},
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .clearFocusOnKeyboardDismiss(),
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        shape = RoundedCornerShape(30.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        leadingIcon = {
            IconButton(onClick = onClickBack) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
        },
        trailingIcon = {
            IconButton(onClick = onClickMap) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
            }
        },
        placeholder = {
            InfinityText(
                texts = placeholder,
                delayTime = 4000L,
                content = { targetState ->
                    Text(
                        text = targetState,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
            )
        },
    )
}
