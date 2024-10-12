package com.party.data.datasource.remote.user

import com.party.data.entity.user.detail.LocationEntity
import com.party.data.entity.user.detail.SaveInterestLocationEntity
import com.party.data.entity.user.auth.SocialLoginEntity
import com.party.data.entity.user.auth.UserSignUpEntity
import com.party.data.entity.user.detail.PersonalityListEntity
import com.party.data.entity.user.detail.PositionListEntity
import com.party.data.entity.user.detail.SaveCarrierEntity
import com.party.data.entity.user.detail.SaveCarrierEntity1
import com.party.data.service.UserService
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.signup.UserSignUpRequest
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class UserRemoteSourceImpl @Inject constructor(
    private val userService: UserService,
): UserRemoteSource{
    override suspend fun googleLogin(accessToken: String): ApiResponse<SocialLoginEntity> {
        return userService.loginGoogle(accessToken = accessToken)
    }

    override suspend fun kakaoLogin(accessToken: String): ApiResponse<SocialLoginEntity> {
        return userService.loginKakao(accessToken = accessToken)
    }

    override suspend fun checkNickName(
        signupAccessToken: String,
        nickname: String
    ): ApiResponse<String> {
        return userService.checkNickName(signupAccessToken = signupAccessToken, nickname = nickname)
    }

    override suspend fun userSignUp(
        signupAccessToken: String,
        userSignUpRequest: UserSignUpRequest
    ): ApiResponse<UserSignUpEntity> {
        return userService.userSignUp(signupAccessToken = signupAccessToken, userSignUpRequest = userSignUpRequest)
    }

    override suspend fun getLocations(accessToken: String, province: String): ApiResponse<List<LocationEntity>> {
        return userService.getLocations(accessToken = accessToken, province = province)
    }

    override suspend fun saveInterestLocation(
        accessToken: String,
        locations: InterestLocationList,
    ): ApiResponse<List<SaveInterestLocationEntity>> {
        return userService.saveInterestLocation(accessToken = accessToken, locations = locations)
    }

    override suspend fun getPositions(
        accessToken: String,
        main: String
    ): ApiResponse<List<PositionListEntity>> {
        return userService.getPositions(accessToken = accessToken, main = main)
    }

    override suspend fun saveCarrier(
        accessToken: String,
        career: SaveCarrierList
    ): ApiResponse<SaveCarrierEntity1> {
        return userService.saveCareer(accessToken = accessToken, career = career)
    }

    override suspend fun getPersonalities(accessToken: String): ApiResponse<List<PersonalityListEntity>> {
        return userService.getPersonalities(accessToken = accessToken)
    }
}