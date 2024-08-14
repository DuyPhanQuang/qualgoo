package wangyeo.interview.core.extensions

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Dp.Space() = Spacer(
    modifier = Modifier
        .height(this)
)

@Composable
fun SmSpace() = Spacer(modifier = Modifier.height(16.dp))

@Composable
fun MdSpace() = Spacer(modifier = Modifier.height(24.dp))

@Composable
fun LgSpace() = Spacer(modifier = Modifier.height(32.dp))

@Composable
fun XlSpace() = Spacer(modifier = Modifier.height(40.dp))