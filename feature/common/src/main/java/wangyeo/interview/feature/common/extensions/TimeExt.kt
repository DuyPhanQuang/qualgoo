package wangyeo.interview.feature.common.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Long.toDateTime(format: String, zoneId: Int? = null): String {
    val date = Date().apply {
        time = this@toDateTime
    }

    val language = androidx.compose.ui.text.intl.Locale.current.language
    val locale = Locale(language)

    return SimpleDateFormat(format, locale).apply {
        timeZone = TimeZone.getDefault().apply { zoneId?.let { rawOffset = it } }
    }.format(date)
}
