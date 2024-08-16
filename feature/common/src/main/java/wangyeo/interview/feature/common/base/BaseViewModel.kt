package wangyeo.interview.feature.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import wangyeo.interview.domain.dialog.AlertDialog
import wangyeo.interview.domain.exceptions.AppDomainException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseViewModel : ViewModel() {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.message)
        hideLoading()
        val errorResponse = if (exception is AppDomainException) {
            exception
        } else {
            AppDomainException.AlertException(
                code = -1,
                alertDialog = AlertDialog(
                    title = "Unknown",
                    message = exception.toString(),
                ),
            )
        }

        showError(errorResponse)
    }

    private var job: Job? = null

    private var callApi: suspend CoroutineScope.() -> Unit = {}

    open fun showError(error: AppDomainException) {}

    open fun hideError() {}

    open fun hideLoading() {}

    fun retryViewModelScope(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        api: suspend CoroutineScope.() -> Unit,
    ) {
        viewModelScope.launch(context + coroutineExceptionHandler, start) {
            callApi = api

            job = launch {
                callApi.invoke(this)
            }

            job?.join()
        }
    }

    open fun retry() {
        viewModelScope.launch(coroutineExceptionHandler) {
            job?.cancel()
            job = launch {
                callApi.invoke(this)
            }
            job?.join()
        }
    }

    override fun onCleared() {
        job?.cancel()
        job = null
        super.onCleared()
    }
}
