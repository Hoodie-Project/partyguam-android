package com.party.data.repository

import com.party.common.ServerApiResponse
import com.party.common.ServerApiResponse.ErrorResponse
import com.party.common.ServerApiResponse.ExceptionResponse
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.data.datasource.remote.user.UserRemoteSource
import com.party.data.dto.user.auth.SocialLoginErrorDto
import com.party.data.dto.user.auth.SocialLoginSuccessDto
import com.party.data.mapper.UserMapper
import com.party.data.mapper.UserMapper.mapperUserSignUpResponse
import com.party.domain.model.user.SocialLogin
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.Location
import com.party.domain.model.user.detail.PersonalityList
import com.party.domain.model.user.detail.PersonalitySave
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.PositionList
import com.party.domain.model.user.detail.SaveCarrier
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.detail.SaveInterestLocation
import com.party.domain.model.user.party.MyParty
import com.party.domain.model.user.profile.UserProfile
import com.party.domain.model.user.profile.UserProfileModify
import com.party.domain.model.user.recruitment.MyRecruitment
import com.party.domain.model.user.signup.UserSignUp
import com.party.domain.model.user.signup.UserSignUpRequest
import com.party.domain.repository.UserRepository
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.retrofit.errorBody
import com.skydoves.sandwich.retrofit.statusCode
import kotlinx.serialization.json.Json
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteSource: UserRemoteSource,
): UserRepository{
    override suspend fun googleLogin(accessToken: String): ServerApiResponse<SocialLogin> {
        return when(val result = userRemoteSource.googleLogin(accessToken = accessToken)){
            is ApiResponse.Success -> {
                val resultSuccess = Json.decodeFromString<SocialLoginSuccessDto>(result.data.toString())
                SuccessResponse(
                    data = UserMapper.mapperToSocialLoginResponse(resultSuccess),
                )
            }
            is ApiResponse.Failure.Error-> {
                val errorBody = result.errorBody?.string()
                when(result.statusCode){
                    StatusCode.Unauthorized -> { // 401
                        val errorResponse = Json.decodeFromString<SocialLoginErrorDto>(errorBody!!)
                        ErrorResponse(
                            statusCode = StatusCode.Unauthorized.code,
                            data = UserMapper.mapperSocialLoginErrorResponse(errorResponse),
                        )
                    }
                    StatusCode.InternalServerError -> { // 500
                        val errorResponse = Json.decodeFromString<ErrorResponse<SocialLoginErrorDto>>(errorBody!!)
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
                result.throwable.printStackTrace()
                ExceptionResponse(result.message)
            }
        }
    }

    override suspend fun kakaoLogin(accessToken: String): ServerApiResponse<SocialLogin> {
        return when(val result = userRemoteSource.kakaoLogin(accessToken = accessToken)){
            is ApiResponse.Success -> {
                //val resultSuccess = Json.decodeFromString<SocialLoginSuccessEntity>(result.data)
                val resultSuccess = result.data as SocialLoginSuccessDto

                SuccessResponse(
                    data = UserMapper.mapperToSocialLoginResponse(resultSuccess),
                )
            }
            is ApiResponse.Failure.Error-> {
                val errorBody = result.errorBody?.string()
                when(result.statusCode){
                    StatusCode.Unauthorized -> { // 401
                        val errorResponse = Json.decodeFromString<SocialLoginErrorDto>(errorBody!!)
                        ErrorResponse(
                            statusCode = StatusCode.Unauthorized.code,
                            data = UserMapper.mapperSocialLoginErrorResponse(errorResponse),
                        )
                    }
                    StatusCode.InternalServerError -> { // 500
                        val errorResponse = Json.decodeFromString<ErrorResponse<SocialLoginErrorDto>>(errorBody!!)
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
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
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
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun userSignUp(
        signupAccessToken: String,
        userSignUpRequest: UserSignUpRequest
    ): ServerApiResponse<UserSignUp> {
        val result = userRemoteSource.userSignUp(signupAccessToken = signupAccessToken, userSignUpRequest = userSignUpRequest)
        return when(result){
            is ApiResponse.Success -> {
                SuccessResponse(data = mapperUserSignUpResponse(result.data))
            }
            is ApiResponse.Failure.Error-> {
                ErrorResponse()
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }

    }

    override suspend fun getLocations(province: String): ServerApiResponse<List<Location>> {
        return when(val result = userRemoteSource.getLocations(province = province)){
            is ApiResponse.Success -> {
                SuccessResponse(data = result.data.map { UserMapper.mapperToLocationResponse(it) })
            }
            is ApiResponse.Failure.Error-> {
                ErrorResponse()
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun saveInterestLocation(
        locations: InterestLocationList,
    ): ServerApiResponse<List<SaveInterestLocation>> {
        return when(val result = userRemoteSource.saveInterestLocation(locations = locations)) {
            is ApiResponse.Success -> {
                SuccessResponse(
                    data = result.data.map { UserMapper.mapperToSaveInterestLocationResponse(it) }
                )
            }

            is ApiResponse.Failure.Error -> {
                val errorBody = result.errorBody?.string()
                when(result.statusCode){
                    StatusCode.Conflict -> {
                        val errorResponse = Json.decodeFromString<ErrorResponse<SaveInterestLocation>>(errorBody!!)
                        ErrorResponse(
                            statusCode = StatusCode.Conflict.code,
                            message = errorResponse.message,
                        )
                    }
                    else -> ErrorResponse(statusCode = StatusCode.InternalServerError.code)
                }
            }

            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun getPositions(
        main: String
    ): ServerApiResponse<List<PositionList>> {
        return when(val result = userRemoteSource.getPositions(main = main)){
            is ApiResponse.Success -> {
                SuccessResponse(data = result.data.map { UserMapper.mapperToPositionListResponse(it) })
            }
            is ApiResponse.Failure.Error -> {
                ErrorResponse()
            }
            is ApiResponse.Failure.Exception -> {
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun saveCarrier(
        career: SaveCarrierList
    ): ServerApiResponse<List<SaveCarrier>> {
        return when(val result = userRemoteSource.saveCarrier(career = career)){
            is ApiResponse.Success -> {
                SuccessResponse(data = result.data.career.map { UserMapper.mapperToSaveCarrierResponse(it) })
            }
            is ApiResponse.Failure.Error -> {
                val errorBody = result.errorBody?.string()
                when(result.statusCode){
                    StatusCode.Conflict -> {
                        val errorResponse = Json.decodeFromString<ErrorResponse<SaveCarrier>>(errorBody!!)
                        ErrorResponse(
                            statusCode = StatusCode.Conflict.code,
                            message = errorResponse.message,
                        )
                    }
                    else -> ErrorResponse(
                        statusCode = StatusCode.InternalServerError.code,
                        message = "알 수 없는 오류가 발생했습니다."
                    )
                }
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }

        }
    }

    override suspend fun deleteCareer(): ServerApiResponse<Unit> {
        return when(val result = userRemoteSource.deleteCareer()){
            is ApiResponse.Success -> {
                SuccessResponse(data = Unit)
            }
            is ApiResponse.Failure.Error -> {
                when(result.statusCode){
                    StatusCode.Forbidden -> ErrorResponse(statusCode = StatusCode.Forbidden.code)
                    StatusCode.NotFound -> ErrorResponse(statusCode = StatusCode.NotFound.code)
                    StatusCode.InternalServerError -> ErrorResponse(statusCode = StatusCode.InternalServerError.code)
                    else -> ErrorResponse(data = Unit)
                }
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun getPersonalities(): ServerApiResponse<List<PersonalityList>> {
        return when(val result = userRemoteSource.getPersonalities()){
            is ApiResponse.Success -> {
                SuccessResponse(data = result.data.map { UserMapper.mapperToPersonalityListResponse(it) })
            }
            is ApiResponse.Failure.Error -> {
                ErrorResponse()
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun savePersonalities(
        personalitySaveRequest: PersonalitySaveRequest
    ): ServerApiResponse<List<PersonalitySave>> {
        return when(val result = userRemoteSource.savePersonalities(personalitySaveRequest = personalitySaveRequest)){
            is ApiResponse.Success -> {
                SuccessResponse(data = result.data.map { UserMapper.mapperToPersonalitySaveResponse(it) })
            }
            is ApiResponse.Failure.Error -> {
                val errorBody = result.errorBody?.string()
                when(result.statusCode){
                    StatusCode.Conflict -> {
                        val errorResponse = Json.decodeFromString<ErrorResponse<PersonalitySave>>(errorBody!!)
                        ErrorResponse(
                            statusCode = StatusCode.Conflict.code,
                            message = errorResponse.message,
                        )
                    }
                    else -> ErrorResponse(
                        statusCode = StatusCode.InternalServerError.code,
                        message = ""
                    )
                }
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun getMyParties(
        page: Int,
        limit: Int,
        sort: String,
        order: String
    ): ServerApiResponse<MyParty> {
        return when(val result = userRemoteSource.getMyParties(page = page, limit = limit, sort = sort, order = order)){
            is ApiResponse.Success -> {
                SuccessResponse(data = UserMapper.mapperToMyParty(result.data))
            }
            is ApiResponse.Failure.Error -> {
                ErrorResponse()
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun getMyRecruitments(
        page: Int,
        limit: Int,
        sort: String,
        order: String
    ): ServerApiResponse<MyRecruitment> {
        return when(val result = userRemoteSource.getMyRecruitments(page = page, limit = limit, sort = sort, order = order)){
            is ApiResponse.Success -> {
                SuccessResponse(data = UserMapper.mapperToMyRecruitment(result.data))
            }
            is ApiResponse.Failure.Error -> {
                ErrorResponse()
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun getUserProfile(): ServerApiResponse<UserProfile> {
        return when(val result = userRemoteSource.getUserProfile()){
            is ApiResponse.Success -> SuccessResponse(data = UserMapper.mapperToUserProfile(result.data))
            is ApiResponse.Failure.Error -> ErrorResponse()
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun updateUserProfile(
        image: MultipartBody.Part?,
        genderVisible: Boolean?,
        birthVisible: Boolean?,
        portfolioTitle: RequestBody?,
        portfolio: RequestBody?,
    ): ServerApiResponse<UserProfileModify> {
        return when(val result = userRemoteSource.updateUserProfile(
            image = image,
            genderVisible = genderVisible,
            birthVisible = birthVisible,
            portfolioTitle = portfolioTitle,
            portfolio = portfolio
        )){
            is ApiResponse.Success -> {
                SuccessResponse(data = UserMapper.mapperToUserProfileModify(result.data))
            }
            is ApiResponse.Failure.Error -> {
                ErrorResponse()
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun deletePersonalities(personalityQuestionId: Int): ServerApiResponse<Unit> {
        return when(val result = userRemoteSource.deletePersonalities(personalityQuestionId = personalityQuestionId)){
            is ApiResponse.Success -> {
                SuccessResponse(data = Unit)
            }
            is ApiResponse.Failure.Error -> {
                when(result.statusCode){
                    StatusCode.Forbidden -> ErrorResponse(statusCode = StatusCode.Forbidden.code)
                    StatusCode.NotFound -> ErrorResponse(statusCode = StatusCode.NotFound.code)
                    StatusCode.InternalServerError -> ErrorResponse(statusCode = StatusCode.InternalServerError.code)
                    else -> ErrorResponse(data = Unit)
                }
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }

        }
    }

    override suspend fun deleteInterestLocation(): ServerApiResponse<Unit> {
        return when(val result = userRemoteSource.deleteInterestLocation()){
            is ApiResponse.Success -> {
                SuccessResponse(data = Unit)
            }
            is ApiResponse.Failure.Error -> {
                when(result.statusCode){
                    StatusCode.Forbidden -> ErrorResponse(statusCode = StatusCode.Forbidden.code)
                    StatusCode.NotFound -> ErrorResponse(statusCode = StatusCode.NotFound.code)
                    StatusCode.InternalServerError -> ErrorResponse(statusCode = StatusCode.InternalServerError.code)
                    else -> ErrorResponse(data = Unit)
                }
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }
    }
}