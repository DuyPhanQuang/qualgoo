package wangyeo.interview.domain.exceptions

import wangyeo.interview.core.enums.RedirectType
import wangyeo.interview.core.models.Tag
import wangyeo.interview.domain.dialog.AlertDialog
import wangyeo.interview.domain.enums.ExceptionType

sealed class AppDomainException(
    open val code: Int,
    val type: ExceptionType,
    override val message: String?,
) : Throwable(message) {

    data class AlertException(
        override val code: Int,
        val alertDialog: AlertDialog,
    ) : AppDomainException(code, ExceptionType.AlertDialog, null)

    data class InlineException(
        override val code: Int,
        val tags: List<Tag>,
    ) : AppDomainException(code, ExceptionType.Inline, null)

    data class RedirectException(
        override val code: Int,
        val redirect: RedirectType,
    ) : AppDomainException(code, ExceptionType.Redirect, null)

    data class SnackBarException(
        override val code: Int = -1,
        override val message: String,
    ) : AppDomainException(code, ExceptionType.Snack, message)

    data class ToastException(
        override val code: Int,
        override val message: String,
    ) : AppDomainException(code, ExceptionType.Toast, message)

    data class OnPageException(
        override val code: Int,
        override val message: String,
    ) : AppDomainException(code, ExceptionType.OnPage, message)
}
