package com.bksd.brandapp.network.extension

import com.bksd.brandapp.core_domain.DataError
import com.bksd.brandapp.core_domain.DataResult
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): DataResult<T, DataError.Remote> {
    val response = runCatching { execute() }.getOrElse { e ->
        return when (e) {
            is SocketTimeoutException -> DataResult.Failure(DataError.Remote.REQUEST_TIMEOUT)
            is UnresolvedAddressException -> DataResult.Failure(DataError.Remote.NO_INTERNET)
            else -> {
                coroutineContext.ensureActive()
                DataResult.Failure(DataError.Remote.UNKNOWN)
            }
        }
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): DataResult<T, DataError.Remote> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                DataResult.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                DataResult.Failure(DataError.Remote.SERIALIZATION)
            }
        }

        408 -> DataResult.Failure(DataError.Remote.REQUEST_TIMEOUT)
        429 -> DataResult.Failure(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> DataResult.Failure(DataError.Remote.SERVER)
        else -> DataResult.Failure(DataError.Remote.UNKNOWN)
    }
}