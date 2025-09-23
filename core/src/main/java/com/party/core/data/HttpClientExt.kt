package com.party.core.data

import com.party.core.domain.DataError
import com.party.core.domain.DataErrorRemote
import com.party.core.domain.Result
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalSerializationApi::class)
suspend inline fun <reified T, reified E> safeCall(
    execute: () -> Response<T>
): Result<T, DataErrorRemote<E>> {
    return try {
        val response = execute()
        responseToResult(response)
    } catch (e: IOException) {
        println("safeCall NO_INTERNET Error $e")
        Result.Error(DataErrorRemote.NoInternet())
    } catch (e: HttpException) {
        println("safeCall SERVER Error $e")
        Result.Error(DataErrorRemote.ServerError())
    } catch (e: MissingFieldException) {
        println("safeCall SERIALIZATION Error $e")
        Result.Error(DataErrorRemote.Serialization())
    } catch (e: Exception) {
        println("safeCall UNKNOWN Error $e")
        coroutineContext.ensureActive()
        Result.Error(DataErrorRemote.Unknown())
    }
}

inline fun <reified T, reified E> responseToResult(
    response: Response<T>
): Result<T, DataErrorRemote<E>> {
    return when(response.code()) {
        in 200..299 -> {
            runCatching { response.body() }.getOrNull()?.let { body ->
                Result.Success(body)
            } ?: Result.Error(DataErrorRemote.Serialization())
        }
        401 -> Result.Error(DataErrorRemote.Unauthorized())
        408 -> Result.Error(DataErrorRemote.RequestTimeout())
        409 -> Result.Error(DataErrorRemote.Conflict())
        429 -> Result.Error(DataErrorRemote.TooManyRequests())
        in 500..599 -> Result.Error(DataErrorRemote.ServerError())
        else -> Result.Error(DataErrorRemote.Unknown())
    }
}