package com.party.data.datasource.remote.user

import com.party.common.BaseSuccessResponse
import com.party.data.entity.user.SocialLoginEntity
import com.party.data.service.UserService
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class UserRemoteSourceImpl @Inject constructor(
    private val userService: UserService,
): UserRemoteSource{
    override suspend fun googleLogin(accessToken: String): ApiResponse<BaseSuccessResponse<SocialLoginEntity>> {
        return userService.loginGoogle(accessToken = accessToken)
    }

    override suspend fun kakaoLogin(accessToken: String): ApiResponse<BaseSuccessResponse<SocialLoginEntity>> {
        return userService.loginKakao(accessToken = accessToken)
    }
}