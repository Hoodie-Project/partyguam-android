package com.party.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.party.data.service.UserService
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val TIME_OUT_VALUE: Long = 3000

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val json = Json {
        isLenient = true // Json 큰 따옴표 느슨하게 체크
        ignoreUnknownKeys = true // Filed 값이 없는 경우 무시
        coerceInputValues = true // "null이 들어간 경우 dafault 값으로 변경"
    }

    @Singleton
    @Provides
    fun tokenLogging(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            //.addInterceptor(authInterceptor)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .connectTimeout(TIME_OUT_VALUE, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_VALUE, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_VALUE, TimeUnit.SECONDS)
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideUserServiceApi(): UserService {
        return Retrofit.Builder()
            .baseUrl("https://partyguam.net/dev/")
            .client(tokenLogging())
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
            .create(UserService::class.java)
    }
}