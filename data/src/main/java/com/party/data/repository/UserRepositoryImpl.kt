package com.party.data.repository

import com.party.common.BaseErrorResponse
import com.party.common.BaseExceptionResponse
import com.party.common.BaseSuccessResponse
import com.party.common.ServerApiResponse
import com.party.data.datasource.remote.user.UserRemoteSource
import com.party.data.mapper.UserMapper
import com.party.domain.model.member.SocialLoginRequest
import com.party.domain.model.member.SocialLoginResponse
import com.party.domain.repository.UserRepository
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.retrofit.errorBody
import kotlinx.serialization.json.Json
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteSource: UserRemoteSource,
): UserRepository{
    override suspend fun googleLogin(socialLoginRequest: SocialLoginRequest): ServerApiResponse {
        return when(val result = userRemoteSource.googleLogin(socialLoginRequest = socialLoginRequest)){
            is ApiResponse.Success -> {
                BaseSuccessResponse(
                    code = "",
                    message = "",
                    data = UserMapper.mapperToSocialLoginResponse(result.data.data!!),
                )
            }
            is ApiResponse.Failure.Error-> {
                val errorBody = result.errorBody?.string()
                val errorResponse = Json.decodeFromString<BaseErrorResponse>(errorBody!!)
                BaseErrorResponse(
                    message = errorResponse.message,
                    error = errorResponse.error,
                    statusCode = errorResponse.statusCode,
                    path = errorResponse.path,
                    timestamp = errorResponse.timestamp,
                )
            }
            is ApiResponse.Failure.Exception -> {
                BaseExceptionResponse(
                    message = result.message,
                )
            }
        }
    }

    override suspend fun kakaoLogin(socialLoginRequest: SocialLoginRequest): BaseSuccessResponse<SocialLoginResponse> {
        TODO("Not yet implemented")
    }
}