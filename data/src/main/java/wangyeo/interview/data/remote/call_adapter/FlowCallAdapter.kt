package wangyeo.interview.data.remote.call_adapter

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import wangyeo.interview.data.exceptions.ResponseException
import wangyeo.interview.data.mapper.AppDataExceptionMapper
import java.io.IOException
import java.lang.reflect.Type
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FlowCallAdapter<T>(
    private val retrofit: Retrofit,
    private val mapper: AppDataExceptionMapper,
    private val responseType: Type,
) : CallAdapter<T, Flow<T>> {
    override fun responseType() = responseType

    override fun adapt(call: Call<T>): Flow<T> = flow {
        emit(suspendCancellableCoroutine { cancellableContinuation ->
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    try {
                        cancellableContinuation.resume(response.body()!!)
                    } catch (e: Exception) {
                        cancellableContinuation.resumeWithException(
                            mapper.mapperToAppDomainException(asResponseException(e, response))
                        )
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    cancellableContinuation.resumeWithException(
                        mapper.mapperToAppDomainException(asResponseException(t)))
                }
            })

            cancellableContinuation.invokeOnCancellation { call.cancel() }
        })
    }

    private fun asResponseException(
        throwable: Throwable,
        res: Response<T>? = null,
    ): ResponseException = when {
        throwable is HttpException -> {
            val response = throwable.response()

            if (throwable.code() == 422) ResponseException.httpObject(response, retrofit)
            else ResponseException.http(response, retrofit)
        }
        res != null -> {
            ResponseException.httpObject(res, retrofit)
        }
        throwable is IOException -> {
            ResponseException.network(throwable)
        }
        else -> {
            ResponseException.unexpected(throwable)
        }
    }
}
