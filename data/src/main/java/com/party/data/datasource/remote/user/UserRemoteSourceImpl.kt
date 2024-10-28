package com.party.data.datasource.remote.user

import com.party.data.entity.user.auth.SocialLoginEntity
import com.party.data.entity.user.auth.UserSignUpEntity
import com.party.data.entity.user.detail.LocationEntity
import com.party.data.entity.user.detail.PersonalityListEntity
import com.party.data.entity.user.detail.PersonalitySaveEntity
import com.party.data.entity.user.detail.PositionListEntity
import com.party.data.entity.user.detail.SaveCarrierEntity1
import com.party.data.entity.user.detail.SaveInterestLocationEntity
import com.party.data.service.NoTokenService
import com.party.data.service.UserService
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.signup.UserSignUpRequest
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class UserRemoteSourceImpl @Inject constructor(
    private val noTokenService: NoTokenService,
    private val userService: UserService,
): UserRemoteSource{
    override suspend fun googleLogin(accessToken: String): ApiResponse<SocialLoginEntity> {
        return noTokenService.loginGoogle(accessToken = accessToken)
    }

    override suspend fun kakaoLogin(accessToken: String): ApiResponse<SocialLoginEntity> {
        return noTokenService.loginKakao(accessToken = accessToken)
    }

    override suspend fun checkNickName(
        signupAccessToken: String,
        nickname: String
    ): ApiResponse<String> {
        return noTokenService.checkNickName(signupAccessToken = signupAccessToken, nickname = nickname)
    }

    override suspend fun userSignUp(
        signupAccessToken: String,
        userSignUpRequest: UserSignUpRequest
    ): ApiResponse<UserSignUpEntity> {
        return noTokenService.userSignUp(signupAccessToken = signupAccessToken, userSignUpRequest = userSignUpRequest)
    }

    override suspend fun getLocations(province: String): ApiResponse<List<LocationEntity>> {
        return userService.getLocations(province = province)
    }

    override suspend fun saveInterestLocation(
        locations: InterestLocationList,
    ): ApiResponse<List<SaveInterestLocationEntity>> {
        return userService.saveInterestLocation(locations = locations)
    }

    override suspend fun getPositions(
        main: String
    ): ApiResponse<List<PositionListEntity>> {
        return userService.getPositions(main = main)
    }

    override suspend fun saveCarrier(
        career: SaveCarrierList
    ): ApiResponse<SaveCarrierEntity1> {
        return userService.saveCareer(career = career)
    }

    override suspend fun getPersonalities(): ApiResponse<List<PersonalityListEntity>> {
        return userService.getPersonalities()
    }

    override suspend fun savePersonalities(
        personalitySaveRequest: PersonalitySaveRequest
    ): ApiResponse<List<PersonalitySaveEntity>> {
        return userService.savePersonalities(personalitySaveRequest = personalitySaveRequest)
    }
}