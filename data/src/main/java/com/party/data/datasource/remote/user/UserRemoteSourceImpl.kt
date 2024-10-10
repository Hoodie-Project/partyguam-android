package com.party.data.datasource.remote.user

import com.party.data.entity.user.LocationEntity
import com.party.data.entity.user.SaveInterestLocationEntity
import com.party.data.entity.user.auth.SocialLoginEntity
import com.party.data.entity.user.auth.UserSignUpEntity
import com.party.data.service.UserService
import com.party.domain.model.user.detail.InterestLocationRequest
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
        locations: List<InterestLocationRequest>
    ): ApiResponse<SaveInterestLocationEntity> {
        return userService.saveInterestLocation(accessToken = accessToken, locations = locations)
    }
}