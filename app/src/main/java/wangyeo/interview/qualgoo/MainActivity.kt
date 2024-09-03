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
import androidx.lifecycle.Observer
import io.flutter.embedding.android.FlutterActivity

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
            println(result)
        }

        setContent {
            AppTheme {
                viewModel.goToFlutterActivity.observe(this, Observer { state ->
                    if (state.isStarted) {
                        viewModel.onOpenFlutterComplete() // Reset the event if needed
                        startFlutterActivity(state.arguments)
                    }
                })

                RootApp()
            }
        }
    }

    private fun startFlutterActivity(arguments: String? = null) {
        val intent = FlutterActivity.withCachedEngine("1").build(this)
        intent.putExtra("arguments", arguments)
        startActivity(intent)
    }
}
