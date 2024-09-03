package wangyeo.interview.qualgoo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import wangyeo.interview.theme.AppTheme
import androidx.activity.viewModels
import wangyeo.interview.qualgoo.bridges.flutter.FlutterChannelKind
import wangyeo.interview.qualgoo.bridges.flutter.FlutterUtils

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val channelFlutterViewModel: ChannelFlutterViewModel by viewModels()
    private lateinit var startFlutterActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Initialize the activity result launcher
        startFlutterActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // Handle the result if needed
            println("activity launcher result: $result")
        }

        channelFlutterViewModel.state.observe(this) { value ->
            value?.let {
                if (it.isStarted) {
                    when (it.kind) {
                        FlutterChannelKind.ALIBABA -> {

                        }
                        FlutterChannelKind.INTERNAL -> {
                            channelFlutterViewModel.onOpenFlutterComplete() // Reset the event if needed
                            FlutterUtils.internalLaunchFlutterActivityFunc(it.arguments)
                        }
                    }
                }
            }
        }

        setContent {
            AppTheme {
                RootApp(
                    channelFlutterViewModel = channelFlutterViewModel
                )
            }
        }
    }
}
