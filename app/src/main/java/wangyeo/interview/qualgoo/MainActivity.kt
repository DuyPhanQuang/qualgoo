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
import io.flutter.embedding.android.FlutterActivity
import wangyeo.interview.qualgoo.bridges.flutter.FlutterChannelKind

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ChannelFlutterViewModel by viewModels()
    private lateinit var startFlutterActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Initialize the activity result launcher
        startFlutterActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // Handle the result if needed
            println("activity launcher result: $result")
        }

        viewModel.state.observe(this) { value ->
            value?.let {
                if (it.isStarted) {
                    when (it.kind) {
                        FlutterChannelKind.ALIBABA -> {

                        }
                        FlutterChannelKind.INTERNAL -> {
                            viewModel.onOpenFlutterComplete() // Reset the event if needed
                            startFlutterActivity(value.arguments)
                        }
                    }
                }
            }
        }

        setContent {
            AppTheme {
                RootApp(channelFlutterViewModel = viewModel)
            }
        }
    }

    private fun startFlutterActivity(arguments: String? = null) {
        val intent = FlutterActivity
            .withCachedEngine("1")
            .build(this)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("arguments", arguments)
        startActivity(intent)
    }
}
