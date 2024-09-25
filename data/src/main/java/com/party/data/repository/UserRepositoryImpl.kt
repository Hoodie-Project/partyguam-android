package com.party.data.repository

import com.party.common.ServerApiResponse
import com.party.common.ServerApiResponse.BaseErrorResponse
import com.party.common.ServerApiResponse.BaseExceptionResponse
import com.party.common.ServerApiResponse.BaseSuccessResponse
import com.party.data.datasource.remote.user.UserRemoteSource
import com.party.data.entity.user.SocialLoginErrorEntity
import com.party.data.mapper.UserMapper
import com.party.domain.repository.UserRepository
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.retrofit.errorBody
import com.skydoves.sandwich.retrofit.statusCode
import kotlinx.serialization.json.Json
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteSource: UserRemoteSource,
): UserRepository{
    override suspend fun googleLogin(accessToken: String): ServerApiResponse {
        return when(val result = userRemoteSource.googleLogin(accessToken = accessToken)){
            is ApiResponse.Success -> {
                BaseSuccessResponse(
                    code = "",
                    message = "",
                    data = UserMapper.mapperToSocialLoginResponse(result.data.data!!),
                )
            }
            is ApiResponse.Failure.Error-> {
                val errorBody = result.errorBody?.string()
                when(result.statusCode){
                    StatusCode.Unauthorized -> { // 401
                        val errorResponse = Json.decodeFromString<SocialLoginErrorEntity>(errorBody!!)
                        BaseErrorResponse(
                            statusCode = StatusCode.Unauthorized.code,
                            data = UserMapper.mapperSocialLoginErrorResponse(errorResponse),
                        )
                    }
                    StatusCode.InternalServerError -> { // 500
                        val errorResponse = Json.decodeFromString<BaseErrorResponse<SocialLoginErrorEntity>>(errorBody!!)
                        BaseErrorResponse(
                            message = errorResponse.message,
                            error = errorResponse.error,
                            statusCode = errorResponse.statusCode,
                            path = errorResponse.path,
                            timestamp = errorResponse.timestamp,
                            data = null,
                        )
                    }
                    else -> {
                        BaseErrorResponse(
                            data = null,
                        )
                    }
                }
            }
            is ApiResponse.Failure.Exception -> {
                BaseExceptionResponse(
                    message = result.message,
                )
            }
        }
    }

    override suspend fun kakaoLogin(accessToken: String): ServerApiResponse {
        return when(val result = userRemoteSource.kakaoLogin(accessToken = accessToken)){
            is ApiResponse.Success -> {
                BaseSuccessResponse(
                    code = "",
                    message = "",
                    data = UserMapper.mapperToSocialLoginResponse(result.data.data!!),
                )
            }
            is ApiResponse.Failure.Error-> {
                val errorBody = result.errorBody?.string()
                when(result.statusCode){
                    StatusCode.Unauthorized -> { // 401
                        val errorResponse = Json.decodeFromString<SocialLoginErrorEntity>(errorBody!!)
                        BaseErrorResponse(
                            statusCode = StatusCode.Unauthorized.code,
                            data = UserMapper.mapperSocialLoginErrorResponse(errorResponse),
                        )
                    }
                    StatusCode.InternalServerError -> { // 500
                        val errorResponse = Json.decodeFromString<BaseErrorResponse<SocialLoginErrorEntity>>(errorBody!!)
                        BaseErrorResponse(
                            message = errorResponse.message,
                            error = errorResponse.error,
                            statusCode = errorResponse.statusCode,
                            path = errorResponse.path,
                            timestamp = errorResponse.timestamp,
                            data = null,
                        )
                    }
                    else -> {
                        BaseErrorResponse(
                            data = null,
                        )
                    }
                }
            }
            is ApiResponse.Failure.Exception -> {
                BaseExceptionResponse(
                    message = result.message,
                )
            }
        }
    }
}