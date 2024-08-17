package wangyeo.interview.data.mapper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import wangyeo.interview.core.enums.ActionType
import wangyeo.interview.data.exceptions.ResponseException
import wangyeo.interview.data.R
import wangyeo.interview.data.dialog.AlertDialog
import wangyeo.interview.data.exceptions.AppDomainException
import javax.inject.Inject

class AppDataExceptionMapper @Inject constructor(@ApplicationContext private val context: Context) {
    fun mapperToAppDomainException(throwable: ResponseException): Throwable = when (throwable.getKind()) {
        ResponseException.Kind.NETWORK -> AppDomainException.SnackBarException(
            code = -1,
            message = context.getString(R.string.error_message_network),
        )

        ResponseException.Kind.PREFERENCE -> AppDomainException.SnackBarException(
            code = -1,
            message = context.getString(R.string.error_message_preferences),
        )

        ResponseException.Kind.HTTP -> {
            val errorCode = throwable.getResponse()?.code() ?: -1
            val errorUrl = throwable.getRetrofit()?.baseUrl() ?: context.getString(R.string.invalid)

            AppDomainException.AlertException(
                code = errorCode,
                alertDialog = AlertDialog(
                    title = context.getString(R.string.error_title_http),
                    message = context.getString(R.string.error_message_http, errorCode, errorUrl),
                    positiveAction = ActionType.RETRY_API,
                    positiveMessage = context.getString(R.string.retry)
                ),
            )
        }

        ResponseException.Kind.HTTP_WITH_OBJECT -> {
            val errorCode: Int = throwable.getResponse()?.code() ?: -1
            val errorUrl = throwable.getRetrofit()?.baseUrl() ?: context.getString(R.string.invalid)

            AppDomainException.AlertException(
                code = errorCode,
                alertDialog = AlertDialog(
                    title = context.getString(R.string.error_title_http),
                    message = context.getString(R.string.error_message_http, errorCode, errorUrl),
                    positiveAction = ActionType.RETRY_API,
                    positiveMessage = context.getString(R.string.retry)
                ),
            )
        }

        else -> throwable
    }
}
