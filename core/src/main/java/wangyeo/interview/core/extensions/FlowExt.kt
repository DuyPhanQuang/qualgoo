package wangyeo.interview.core.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine

fun <T> Throwable.asFlow(): Flow<T> = flow {
    emit(suspendCancellableCoroutine { cancellableContinuation ->
        cancellableContinuation.cancel(this@asFlow)
    })
}
