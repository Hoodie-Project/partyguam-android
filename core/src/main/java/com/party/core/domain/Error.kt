package com.party.core.domain

interface Error

sealed interface DataError: Error

sealed class DataErrorRemote<E>(val response: E? = null): DataError {
    class Unauthorized<E>(response: E? = null) : DataErrorRemote<E>(response)
    class RequestTimeout<E>(response: E? = null) : DataErrorRemote<E>(response)
    class Conflict<E>(response: E? = null) : DataErrorRemote<E>(response)
    class TooManyRequests<E>(response: E? = null) : DataErrorRemote<E>(response)
    class NoInternet<E>(response: E? = null) : DataErrorRemote<E>(response)
    class ServerError<E>(response: E? = null) : DataErrorRemote<E>(response)
    class Serialization<E>(response: E? = null) : DataErrorRemote<E>(response)
    class BadRequest<E>(response: E? = null) : DataErrorRemote<E>(response)
    class Unknown<E>(response: E? = null) : DataErrorRemote<E>(response)
}