package com.party.data.datasource.remote.user

import com.party.common.ServerApiResponse
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.data.entity.user.SocialLoginEntity
import com.party.data.service.UserService
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class UserRemoteSourceImpl @Inject constructor(
    private val userService: UserService,
): UserRemoteSource{
    override suspend fun googleLogin(accessToken: String): ApiResponse<ServerApiResponse<SocialLoginEntity>> {
        return userService.loginGoogle(accessToken = accessToken)
    }

    override suspend fun kakaoLogin(accessToken: String): ApiResponse<ServerApiResponse<SocialLoginEntity>> {
        return userService.loginKakao(accessToken = accessToken)
    }

    override suspend fun checkNickName(
        signupAccessToken: String,
        nickname: String
    ): ApiResponse<ServerApiResponse<Unit>> {
        return userService.checkNickName(signupAccessToken = signupAccessToken, nickname = nickname)
    }
}