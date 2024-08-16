package wangyeo.interview.qualgoo

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class RootApplication : Application() {

    override fun onCreate() {
        super.onCreate()
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