package wangyeo.interview.data.mapper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import wangyeo.interview.core.enums.ActionType
import wangyeo.interview.data.exceptions.ResponseException
import wangyeo.interview.domain.dialog.AlertDialog
import wangyeo.interview.domain.exceptions.AppDomainException
import wangyeo.interview.data.R
import javax.inject.Inject

class AppDataExceptionMapper @Inject constructor(@ApplicationContext private val context: Context) {
    fun mapperToAppDomainException(throwable: ResponseException): Throwable = when (throwable.getKind()) {
        ResponseException.Kind.NETWORK -> AppDomainException.SnackBarException(
            code = -1,
            message = "",
        )

        ResponseException.Kind.PREFERENCE -> AppDomainException.SnackBarException(
            code = -1,
            message = "",
        )

        ResponseException.Kind.HTTP -> {
            val errorCode = throwable.getResponse()?.code() ?: -1
            val errorUrl = throwable.getRetrofit()?.baseUrl() ?: ""

            AppDomainException.AlertException(
                code = errorCode,
                alertDialog = AlertDialog(
                    title = "",
                    message = "",
                    positiveAction = ActionType.RETRY_API,
                    positiveMessage = ""
                ),
            )
        }

        ResponseException.Kind.HTTP_WITH_OBJECT -> {
            val errorCode: Int = throwable.getResponse()?.code() ?: -1
            val errorUrl = throwable.getRetrofit()?.baseUrl() ?: ""

            AppDomainException.AlertException(
                code = errorCode,
                alertDialog = AlertDialog(
                    title = "",
                    message = "",
                    positiveAction = ActionType.RETRY_API,
                    positiveMessage = ""
                ),
            )
        }

        else -> throwable
    }
}
