package wangyeo.interview.core.extensions

import android.content.Context
import java.util.Locale

fun Context.getSystemLocale(): Locale =
    this.resources.configuration.locales[0]
