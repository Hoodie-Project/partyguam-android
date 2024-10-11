package com.party.data.repository

import com.party.common.ServerApiResponse
import com.party.common.ServerApiResponse.ErrorResponse
import com.party.common.ServerApiResponse.ExceptionResponse
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.data.datasource.remote.user.UserRemoteSource
import com.party.data.entity.user.auth.SocialLoginErrorEntity
import com.party.data.entity.user.auth.SocialLoginSuccessEntity
import com.party.data.mapper.UserMapper
import com.party.data.mapper.UserMapper.mapperUserSignUpResponse
import com.party.domain.model.user.detail.LocationResponse
import com.party.domain.model.user.SocialLoginResponse
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.SaveInterestLocationResponse
import com.party.domain.model.user.signup.UserSignUpRequest
import com.party.domain.model.user.signup.UserSignUpResponse
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
    override suspend fun googleLogin(accessToken: String): ServerApiResponse<SocialLoginResponse> {
        return when(val result = userRemoteSource.googleLogin(accessToken = accessToken)){
            is ApiResponse.Success -> {
                val resultSuccess = Json.decodeFromString<SocialLoginSuccessEntity>(result.data.toString())
                SuccessResponse(
                    data = UserMapper.mapperToSocialLoginResponse(resultSuccess),
                )
            }
            is ApiResponse.Failure.Error-> {
                val errorBody = result.errorBody?.string()
                when(result.statusCode){
                    StatusCode.Unauthorized -> { // 401
                        val errorResponse = Json.decodeFromString<SocialLoginErrorEntity>(errorBody!!)
                        ErrorResponse(
                            statusCode = StatusCode.Unauthorized.code,
                            data = UserMapper.mapperSocialLoginErrorResponse(errorResponse),
                        )
                    }
                    StatusCode.InternalServerError -> { // 500
                        val errorResponse = Json.decodeFromString<ErrorResponse<SocialLoginErrorEntity>>(errorBody!!)
                        ErrorResponse(
                            message = errorResponse.message,
                            error = errorResponse.error,
                            statusCode = errorResponse.statusCode,
                            path = errorResponse.path,
                            timestamp = errorResponse.timestamp,
                        )
                    }
                    else -> ErrorResponse(data = null)
                }
            }
            is ApiResponse.Failure.Exception -> {
                ExceptionResponse(
                    result.message,
                )
            }
        }
    }

    override suspend fun kakaoLogin(accessToken: String): ServerApiResponse<SocialLoginResponse> {
        return when(val result = userRemoteSource.kakaoLogin(accessToken = accessToken)){
            is ApiResponse.Success -> {
                //val resultSuccess = Json.decodeFromString<SocialLoginSuccessEntity>(result.data)
                val resultSuccess = result.data as SocialLoginSuccessEntity

                SuccessResponse(
                    data = UserMapper.mapperToSocialLoginResponse(resultSuccess),
                )
            }
            is ApiResponse.Failure.Error-> {
                val errorBody = result.errorBody?.string()
                when(result.statusCode){
                    StatusCode.Unauthorized -> { // 401
                        val errorResponse = Json.decodeFromString<SocialLoginErrorEntity>(errorBody!!)
                        ErrorResponse(
                            statusCode = StatusCode.Unauthorized.code,
                            data = UserMapper.mapperSocialLoginErrorResponse(errorResponse),
                        )
                    }
                    StatusCode.InternalServerError -> { // 500
                        val errorResponse = Json.decodeFromString<ErrorResponse<SocialLoginErrorEntity>>(errorBody!!)
                        ErrorResponse(
                            message = errorResponse.message,
                            error = errorResponse.error,
                            statusCode = errorResponse.statusCode,
                            path = errorResponse.path,
                            timestamp = errorResponse.timestamp,
                        )
                    }
                    else -> ErrorResponse(data = null)
                }
            }
            is ApiResponse.Failure.Exception -> {
                ExceptionResponse(
                    message = result.message,
                )
            }
        }
    }

    override suspend fun checkNickName(
        signupAccessToken: String,
        nickname: String
    ): ServerApiResponse<String> {
        val result = userRemoteSource.checkNickName(signupAccessToken = signupAccessToken, nickname = nickname)
        return when(result){
            is ApiResponse.Success -> {
                SuccessResponse(data = result.data)
            }
            is ApiResponse.Failure.Error-> {
                val errorBody = result.errorBody?.string()
                when(result.statusCode){
                    StatusCode.Conflict -> {
                        val errorResponse = Json.decodeFromString<ErrorResponse<String>>(errorBody!!)
                        ErrorResponse(statusCode = StatusCode.Conflict.code, message = errorResponse.message)
                    }
                    StatusCode.Unauthorized -> ErrorResponse(statusCode = StatusCode.Unauthorized.code)
                    else -> ErrorResponse(statusCode = StatusCode.Unauthorized.code)
                }
            }
            is ApiResponse.Failure.Exception -> {
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun userSignUp(
        signupAccessToken: String,
        userSignUpRequest: UserSignUpRequest
    ): ServerApiResponse<UserSignUpResponse> {
        val result = userRemoteSource.userSignUp(signupAccessToken = signupAccessToken, userSignUpRequest = userSignUpRequest)
        return when(result){
            is ApiResponse.Success -> {
                SuccessResponse(data = mapperUserSignUpResponse(result.data))
            }
            is ApiResponse.Failure.Error-> {
                ErrorResponse()
            }
            is ApiResponse.Failure.Exception -> {
                ExceptionResponse(message = result.message)
            }
        }

    }

    override suspend fun getLocations(accessToken: String, province: String): ServerApiResponse<List<LocationResponse>> {
        return when(val result = userRemoteSource.getLocations(accessToken = accessToken, province = province)){
            is ApiResponse.Success -> {
                SuccessResponse(data = result.data.map { UserMapper.mapperToLocationResponse(it) })
            }
            is ApiResponse.Failure.Error-> {
                ErrorResponse()
            }
            is ApiResponse.Failure.Exception -> {
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun saveInterestLocation(
        accessToken: String,
        locations: InterestLocationList,
    ): ServerApiResponse<List<SaveInterestLocationResponse>> {
        return when(val result = userRemoteSource.saveInterestLocation(accessToken = accessToken, locations = locations)) {
            is ApiResponse.Success -> {
                SuccessResponse(
                    data = result.data.map { UserMapper.mapperToSaveInterestLocationResponse(it) }
                )
            }

            is ApiResponse.Failure.Error -> {
                val errorBody = result.errorBody?.string()
                when(result.statusCode){
                    StatusCode.Conflict -> {
                        val errorResponse = Json.decodeFromString<ErrorResponse<SaveInterestLocationResponse>>(errorBody!!)
                        ErrorResponse(
                            statusCode = StatusCode.Conflict.code,
                            message = errorResponse.message,
                        )
                    }
                    else -> ErrorResponse(statusCode = StatusCode.InternalServerError.code)
                }
            }

            is ApiResponse.Failure.Exception -> {
                ExceptionResponse(message = result.message)
            }
        }
    }
}