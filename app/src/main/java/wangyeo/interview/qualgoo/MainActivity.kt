package wangyeo.interview.qualgoo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import wangyeo.interview.theme.AppTheme
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.flutter.embedding.android.FlutterActivity

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ChannelFlutterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()
                state.let {
                    if (it.isStarted) {
                        viewModel.onOpenFlutterComplete() // Reset the event
                        startFlutterActivity()
                    }
                }

                RootApp()
            }
        }
    }

    private fun startFlutterActivity() {
        val intent = FlutterActivity.withCachedEngine("1").build(this)
        startActivity(intent)
    }
}
