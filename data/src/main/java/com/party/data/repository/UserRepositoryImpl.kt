package com.party.data.repository

import com.party.common.ServerApiResponse
import com.party.common.ServerApiResponse.ErrorResponse
import com.party.common.ServerApiResponse.ExceptionResponse
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.core.domain.DataError
import com.party.core.domain.DataErrorRemote
import com.party.core.domain.Result
import com.party.core.domain.map
import com.party.core.domain.onSuccess
import com.party.data.datasource.local.datastore.DataStoreLocalSource
import com.party.data.datasource.remote.user.UserRemoteSource
import com.party.data.dto.user.auth.SocialLoginErrorDto
import com.party.data.dto.user.auth.SocialLoginSuccessDto
import com.party.data.mapper.UserMapper
import com.party.data.mapper.UserMapper.mapperToCheckVersion
import com.party.data.mapper.UserMapper.mapperToLinkGoogle
import com.party.data.mapper.UserMapper.mapperToLinkKakao
import com.party.data.mapper.UserMapper.mapperToMySocialOauth
import com.party.data.mapper.UserMapper.mapperToNotification
import com.party.data.mapper.UserMapper.mapperToReports
import com.party.data.mapper.UserMapper.mapperUserSignUpResponse
import com.party.domain.model.user.AccessTokenRequest
import com.party.domain.model.user.CheckVersion
import com.party.domain.model.user.LinkGoogle
import com.party.domain.model.user.LinkKakao
import com.party.domain.model.user.LinkKakaoRequest
import com.party.domain.model.user.MySocialOauth
import com.party.domain.model.user.notification.Notification
import com.party.domain.model.user.Reports
import com.party.domain.model.user.ReportsRequest
import com.party.domain.model.user.SaveUserFcmTokenRequest
import com.party.domain.model.user.SocialLogin
import com.party.domain.model.user.detail.GetCarrier
import com.party.domain.model.user.detail.GetCarrierPosition
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.Location
import com.party.domain.model.user.detail.ModifyCarrier
import com.party.domain.model.user.detail.ModifyCarrierList
import com.party.domain.model.user.detail.PersonalityList
import com.party.domain.model.user.detail.PersonalitySave
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.PositionList
import com.party.domain.model.user.detail.SaveCarrier
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.detail.SaveInterestLocation
import com.party.domain.model.user.detail.UserLikeLocation
import com.party.domain.model.user.notification.ReadNotification
import com.party.domain.model.user.party.MyParty
import com.party.domain.model.user.profile.UserLocation
import com.party.domain.model.user.profile.UserProfile
import com.party.domain.model.user.profile.UserProfileModify
import com.party.domain.model.user.recruitment.MyRecruitment
import com.party.domain.model.user.signup.UserSignUp
import com.party.domain.model.user.signup.UserSignUpRequest
import com.party.domain.repository.UserRepository
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ApiResponse.Companion.maps
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.retrofit.errorBody
import com.skydoves.sandwich.retrofit.statusCode
import kotlinx.serialization.json.Json
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteSource: UserRemoteSource,
    private val dataStoreLocalSource: DataStoreLocalSource,
): UserRepository{
    override suspend fun googleLogin(accessTokenRequest: AccessTokenRequest): ServerApiResponse<SocialLogin> {
        return when(val result = userRemoteSource.googleLogin(accessTokenRequest = accessTokenRequest)){
            is ApiResponse.Success -> {
                val resultSuccess = result.data as SocialLoginSuccessDto
                SuccessResponse(data = UserMapper.mapperToSocialLoginResponse(resultSuccess))
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
                    StatusCode.Forbidden -> {
                        val errorResponse = Json.decodeFromString<ErrorResponse<SocialLoginErrorDto>>(errorBody!!)
                        ErrorResponse(
                            message = errorResponse.message,
                            error = errorResponse.error,
                            statusCode = errorResponse.statusCode,
                            path = errorResponse.path,
                            timestamp = errorResponse.timestamp,
                            email = errorResponse.email,
                            deletedAt = errorResponse.deletedAt,
                            recoverAccessToken = errorResponse.recoverAccessToken
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
                    StatusCode.Forbidden -> {
                        val errorResponse = Json.decodeFromString<ErrorResponse<SocialLoginErrorDto>>(errorBody!!)
                        ErrorResponse(
                            message = errorResponse.message,
                            error = errorResponse.error,
                            statusCode = errorResponse.statusCode,
                            path = errorResponse.path,
                            timestamp = errorResponse.timestamp,
                            email = errorResponse.email,
                            deletedAt = errorResponse.deletedAt,
                            recoverAccessToken = errorResponse.recoverAccessToken
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

    override suspend fun getMySocialOauth(): ServerApiResponse<List<MySocialOauth>> {
        return when(val result = userRemoteSource.getMySocialOauth()){
            is ApiResponse.Success -> {
                SuccessResponse(data = result.data.map { mapperToMySocialOauth(it) })
            }
            is ApiResponse.Failure.Error-> ErrorResponse()
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun linkKakao(linkKakaoRequest: LinkKakaoRequest): ServerApiResponse<LinkKakao> {
        return when(val result = userRemoteSource.linkKakao(linkKakaoRequest = linkKakaoRequest)){
            is ApiResponse.Success -> {
                SuccessResponse(data = mapperToLinkKakao(result.data))
            }
            is ApiResponse.Failure.Error-> {
                when(result.statusCode){
                    StatusCode.Conflict -> ErrorResponse()
                    else -> ErrorResponse()
                }
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun linkGoogle(accessTokenRequest: AccessTokenRequest): ServerApiResponse<LinkGoogle> {
        return when(val result = userRemoteSource.linkGoogle(accessTokenRequest = accessTokenRequest)){
            is ApiResponse.Success -> SuccessResponse(data = mapperToLinkGoogle(result.data))
            is ApiResponse.Failure.Error-> {
                when(result.statusCode){
                    StatusCode.Conflict -> ErrorResponse()
                    else -> ErrorResponse()
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
    ): Result<String, DataErrorRemote<String>> {
        return userRemoteSource.checkNickName(
            signupAccessToken = signupAccessToken,
            nickname = nickname
        )
    }

    override suspend fun userSignUp(
        signupAccessToken: String,
        userSignUpRequest: UserSignUpRequest
    ): Result<UserSignUp, DataErrorRemote<Unit>> {
        return userRemoteSource.userSignUp(
            signupAccessToken = signupAccessToken,
            userSignUpRequest = userSignUpRequest
        ).map { it.toDomain() }
            .onSuccess { result ->
                val accessToken = result.accessToken
                dataStoreLocalSource.saveAccessToken(accessToken)
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

    override suspend fun getLocationsV2(province: String): Result<List<Location>, DataErrorRemote<Unit>> {
        return userRemoteSource.getLocationsV2(
            province = province
        ).map { it.map { it.toDomain() } }
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

    override suspend fun saveInterestLocationV2(locations: InterestLocationList): Result<List<SaveInterestLocation>, DataErrorRemote<Unit>> {
        return userRemoteSource.saveInterestLocationV2(
            locations = locations
        ).map { it.map { it.toDomain() } }
    }

    override suspend fun getUserLikeLocations(): ServerApiResponse<List<UserLikeLocation>> {
        return when(val result = userRemoteSource.getUserLikeLocations()){
            is ApiResponse.Success -> {
                SuccessResponse(data = result.data.map { UserMapper.mapperToUserLikeLocation(it) })
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
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun getPositionsV2(main: String): Result<List<PositionList>, DataErrorRemote<Unit>> {
        return userRemoteSource.getPositionsV2(main = main).map { it.map { it.toDomain() } }
    }

    override suspend fun getCareer(): ServerApiResponse<List<GetCarrier>> {
        return when(val result = userRemoteSource.getCareers()){
            is ApiResponse.Success -> {
                SuccessResponse(
                    data = result.data.map {
                        GetCarrier(
                            id = it.id,
                            position = GetCarrierPosition(
                                id = it.position.id,
                                main = it.position.main,
                                sub = it.position.sub
                            ),
                            years = it.years,
                            careerType = it.careerType
                        )
                    }
                )
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

    override suspend fun saveCarrier(
        career: SaveCarrierList
    ): ServerApiResponse<List<SaveCarrier>> {
        return when(val result = userRemoteSource.saveCarrier(career = career)){
            is ApiResponse.Success -> {
                SuccessResponse(
                    data = result.data.map { item ->
                        SaveCarrier(
                            id = item.id,
                            positionId = item.positionId,
                            years = item.years,
                            careerType = item.careerType
                        )
                    }
                )
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

    override suspend fun saveCareerV2(career: SaveCarrierList): Result<List<SaveCarrier>, DataErrorRemote<Unit>> {
        return userRemoteSource.saveCareerV2(career = career).map { it.map { it.toDomain() } }
    }

    override suspend fun modifyCarrier(career: ModifyCarrierList): ServerApiResponse<List<ModifyCarrier>> {
        return when(val result = userRemoteSource.modifyCarrier(career = career)){
            is ApiResponse.Success -> {
                SuccessResponse(data = result.data.career.map { UserMapper.mapperToModifyCarrierResponse(it) })
            }
            is ApiResponse.Failure.Error -> {
                val errorBody = result.errorBody?.string()
                when(result.statusCode){
                    StatusCode.Forbidden -> {
                        val errorResponse = Json.decodeFromString<ErrorResponse<ModifyCarrier>>(errorBody!!)
                        ErrorResponse(
                            statusCode = StatusCode.Forbidden.code,
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
        order: String,
        status: String?,
    ): ServerApiResponse<MyParty> {
        return when(val result = userRemoteSource.getMyParties(page = page, limit = limit, sort = sort, order = order, status = status)){
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

    override suspend fun logout(): ServerApiResponse<Unit> {
        return when(val result = userRemoteSource.logout()){
            is ApiResponse.Success -> SuccessResponse(data = Unit)
            is ApiResponse.Failure.Error -> ErrorResponse(data = Unit)
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun signOut(): ServerApiResponse<Unit> {
        return when(val result = userRemoteSource.signOut()){
            is ApiResponse.Success -> SuccessResponse(data = Unit)
            is ApiResponse.Failure.Error -> {
                when(result.statusCode){
                    StatusCode.Forbidden -> {
                        ErrorResponse(
                            statusCode = StatusCode.Forbidden.code,
                            data = null,
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

    override suspend fun reports(reportsRequest: ReportsRequest): ServerApiResponse<Reports> {
        return when(val result = userRemoteSource.reports(reportsRequest = reportsRequest)){
            is ApiResponse.Success -> SuccessResponse(data = mapperToReports(result.data))
            is ApiResponse.Failure.Error -> ErrorResponse()
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(message = result.message)
            }
        }
    }

    override suspend fun recoverAuth(recoverAccessToken: String): ServerApiResponse<SocialLogin> {
        return when(val result = userRemoteSource.recoverAuth(recoverAccessToken = recoverAccessToken)){
            is ApiResponse.Success -> {
                val resultSuccess = result.data as SocialLoginSuccessDto
                SuccessResponse(data = UserMapper.mapperToSocialLoginResponse(resultSuccess))
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
                    else -> ErrorResponse(data = null)
                }
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(result.message)
            }
        }
    }

    override suspend fun getNotifications(
        limit: Int,
        type: String?
    ): ServerApiResponse<Notification> {
        return when(val result = userRemoteSource.getNotifications(limit = limit, type = type)){
            is ApiResponse.Success -> {
                SuccessResponse(data = mapperToNotification(result.data))
            }
            is ApiResponse.Failure.Error-> {
                ErrorResponse(data = null)
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(result.message)
            }
        }
    }

    override suspend fun readNotification(notificationId: Int): ServerApiResponse<ReadNotification> {
        return when(val result = userRemoteSource.readNotification(notificationId = notificationId)){
            is ApiResponse.Success -> {
                SuccessResponse(data = UserMapper.mapperToReadNotification(result.data))
            }
            is ApiResponse.Failure.Error -> {
                ErrorResponse(data = null)
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(result.message)
            }
        }
    }

    override suspend fun deleteNotification(notificationId: Int): ServerApiResponse<Unit> {
        return when(val result = userRemoteSource.deleteNotification(notificationId = notificationId)){
            is ApiResponse.Success -> {
                SuccessResponse(data = Unit)
            }
            is ApiResponse.Failure.Error -> {
                ErrorResponse(data = null)
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(result.message)
            }
        }
    }

    override suspend fun saveUserFcmToken(saveUserFcmTokenRequest: SaveUserFcmTokenRequest): ServerApiResponse<Unit> {
        return when(val result = userRemoteSource.saveUserFcmToken(saveUserFcmTokenRequest = saveUserFcmTokenRequest)){
            is ApiResponse.Success -> {
                SuccessResponse(data = Unit)
            }
            is ApiResponse.Failure.Error -> {
                ErrorResponse(data = null)
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(result.message)
            }
        }
    }

    override suspend fun checkVersion(platform: String): ServerApiResponse<CheckVersion> {
        return when(val result = userRemoteSource.checkVersion(platform = platform)){
            is ApiResponse.Success -> {
                SuccessResponse(data = mapperToCheckVersion(result.data))
            }
            is ApiResponse.Failure.Error -> {
                ErrorResponse(data = null)
            }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(result.message)
            }
        }
    }
}