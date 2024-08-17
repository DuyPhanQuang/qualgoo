import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.search.models.HistorySearchAddressViewData
import com.google.accompanist.flowlayout.FlowRow


@Composable
fun ResultSearchEmptyList(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(
        modifier = modifier.height(60.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
        )
    }
}

@Composable
fun ResultSearchList(
    modifier: Modifier = Modifier,
    listHistory: List<HistorySearchAddressViewData> = emptyList(),
    onClickHistoryItem: (HistorySearchAddressViewData) -> Unit = {},
) {
    FlowRow(
        modifier = modifier.padding(horizontal = 10.dp),
        mainAxisSpacing = 10.dp,
    ) {
        listHistory.forEach {
            ElevatedAssistChip(
                onClick = {
                    onClickHistoryItem(it)
                },
                label = {
                    Text(text = it.address)
                },
            )
        }
    }
}