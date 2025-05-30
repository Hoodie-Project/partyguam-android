package com.party.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.party.data.service.NoTokenService
import com.party.data.service.PartyService
import com.party.data.service.SearchService
import com.party.data.service.UserService
import com.party.domain.model.search.Search
import com.party.domain.usecase.datastore.GetAccessTokenUseCase
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

const val TIME_OUT_VALUE: Long = 5000

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val json = Json {
        encodeDefaults = true // 기본값도 포함
        isLenient = true // Json 큰 따옴표 느슨하게 체크
        ignoreUnknownKeys = true // Filed 값이 없는 경우 무시
        coerceInputValues = true // "null이 들어간 경우 dafault 값으로 변경"
    }

    @Singleton
    @Provides
    fun tokenLogging(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(NetworkPrettyLogger())
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .connectTimeout(TIME_OUT_VALUE, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_VALUE, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_VALUE, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun tokenLogging2(accessTokenInterceptor: AccessTokenInterceptor): OkHttpClient{
        val httpLoggingInterceptor = HttpLoggingInterceptor(NetworkPrettyLogger())
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(accessTokenInterceptor)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .connectTimeout(TIME_OUT_VALUE, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_VALUE, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_VALUE, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideNoTokenApi(): NoTokenService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(tokenLogging())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
            .create(NoTokenService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserServiceApi(accessTokenInterceptor: AccessTokenInterceptor): UserService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(tokenLogging2(accessTokenInterceptor))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
            .create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun providePartyServiceApi(accessTokenInterceptor: AccessTokenInterceptor): PartyService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(tokenLogging2(accessTokenInterceptor))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
            .create(PartyService::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchServiceApi(accessTokenInterceptor: AccessTokenInterceptor): SearchService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(tokenLogging2(accessTokenInterceptor))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
            .create(SearchService::class.java)
    }

    class AccessTokenInterceptor @Inject constructor(
        private val getAccessTokenUseCase: GetAccessTokenUseCase,
    ): Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val token = runBlocking { getAccessTokenUseCase.invoke().first() }
            val request = chain
                .request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            proceed(request)
        }
    }

    class NetworkPrettyLogger: HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            val logName = "[NETWORK-API]"
            if(message.startsWith("{") || message.startsWith("[")){
                try {
                    val prettyPrintJson = GsonBuilder().setPrettyPrinting()
                        .create().toJson(JsonParser.parseString(message))
                    Log.d(logName, prettyPrintJson)
                }catch (e: JsonSyntaxException){
                    Log.d(logName, message)
                }
            }else {
                Log.d(logName, message)
                return
            }
        }
    }
}