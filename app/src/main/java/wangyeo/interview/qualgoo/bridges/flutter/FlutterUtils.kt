package wangyeo.interview.qualgoo.bridges.flutter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import io.flutter.FlutterInjector
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.FlutterEngineGroup
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

const val INTERNAL_ENGINE_ID = "INTERNAL_ENGINE_ID"
const val ALIBABA_ENGINE_ID = "ALIBABA_ENGINE_ID"

@SuppressLint("StaticFieldLeak")
object FlutterUtils {
    lateinit var context: Context

    lateinit var flutterEngines: FlutterEngineGroup
    private lateinit var internalFlutterEngine: FlutterEngine
    private lateinit var alibabaFlutterEngine: FlutterEngine

    lateinit var internalMethodChannel: MethodChannel
    lateinit var alibabaMethodChannel: MethodChannel

    fun initialize() {
        flutterEngines = FlutterEngineGroup(context)

        // [Flutter] - INTERNAL MODULE
        val internalEntrypoint =
            DartExecutor.DartEntrypoint(
                FlutterInjector.instance().flutterLoader().findAppBundlePath(), "internalMain"
            )
        internalFlutterEngine = flutterEngines.createAndRunEngine(context, internalEntrypoint)
        FlutterEngineCache.getInstance().put(INTERNAL_ENGINE_ID, internalFlutterEngine)
        internalMethodChannel =
            MethodChannel(internalFlutterEngine.dartExecutor, "wangyeo.qualgoo.flutter.internal")
        internalMethodChannel.setMethodCallHandler { call, _ ->
            when (call.method) {
                // be careful triggered call this method from flutter module because this will create new Flutter Activity
                // e.g: current route: Main Activity(Native) -> Flutter Activity 1 -> Flutter Activity 2
                "launchFlutterActivity" -> {
                    internalLaunchFlutterActivityFunc(call.arguments as String?)
                }

                "launchNative" -> {
                    internalLaunchNativeFunc(call.arguments as String?)
                }
            }
        }

        // [Flutter] - ALIBABA MODULE
        val alibabaEntrypoint =
            DartExecutor.DartEntrypoint(
                FlutterInjector.instance().flutterLoader().findAppBundlePath(), "alibabaMain"
            )
        alibabaFlutterEngine = flutterEngines.createAndRunEngine(context, alibabaEntrypoint)
        FlutterEngineCache.getInstance().put(ALIBABA_ENGINE_ID, alibabaFlutterEngine)
        alibabaMethodChannel =
            MethodChannel(alibabaFlutterEngine.dartExecutor, "wangyeo.qualgoo.flutter.alibaba")
        alibabaMethodChannel.setMethodCallHandler { call, _ ->
            when (call.method) {
                // be careful triggered call this method from flutter module because this will create new Flutter Activity
                // e.g: current route: Main Activity(Native) -> Flutter Activity 1 -> Flutter Activity 2
                "launchFlutterActivity" -> {
                    alibabaLaunchFlutterActivityFunc(call.arguments as String?)
                }

                "launchNative" -> {
                    alibabaLaunchNativeFunc(call.arguments as String?)
                }
            }
        }
    }

    // arguments should create from native flow
    // sometimes we have arguments should get from callback value `setMethodCallHandler` flutter flow
    fun alibabaLaunchFlutterActivityFunc(arguments: String?) {
        val intent = FlutterActivity
            .withCachedEngine(ALIBABA_ENGINE_ID)
            .build(context)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("arguments", arguments)
        context.startActivity(intent)
    }

    fun internalLaunchNativeFunc(arguments: String?) {
        /// impl
    }

    // arguments should create from native flow
    // sometimes we have arguments should get from callback value `setMethodCallHandler` flutter flow
    fun internalLaunchFlutterActivityFunc(arguments: String?) {
        val intent = FlutterActivity
            .withCachedEngine(INTERNAL_ENGINE_ID)
            .build(context)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("arguments", arguments)
        context.startActivity(intent)
    }

    fun alibabaLaunchNativeFunc(arguments: String?) {
        /// impl
    }
}