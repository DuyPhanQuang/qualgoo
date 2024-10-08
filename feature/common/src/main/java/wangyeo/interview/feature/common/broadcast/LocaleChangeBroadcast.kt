package wangyeo.interview.feature.common.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.ui.text.intl.Locale

class LocaleChangeBroadcast(
    private val onChangeLocale: (Locale) -> Unit,
) : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val locale = Locale.current
        onChangeLocale.invoke(locale)
    }
}