package wangyeo.interview.qualgoo

import android.content.pm.PackageManager
import android.os.Build
import androidx.multidex.MultiDexApplication
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import wangyeo.interview.qualgoo.bridges.flutter.FlutterUtils

@HiltAndroidApp
class RootApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // [Flutter] - Initiate multiple flutter engines
        FlutterUtils.context = this
        FlutterUtils.initialize()

        val appInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getApplicationInfo(
                packageName,
                PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong()),
            )
        } else {
            packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        }
        val value = appInfo.metaData.getString("com.google.android.geo.API_KEY") ?: "123456"

        Places.initialize(this, value)

        val debug = true
        if (debug) {
            Timber.plant(Timber.DebugTree())
        }
    }
}