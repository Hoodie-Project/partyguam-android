package com.party.data.datasource.remote.user

import com.party.common.BaseSuccessResponse
import com.party.data.entity.user.SocialLoginEntity
import com.party.data.service.UserService
import com.party.domain.model.member.SocialLoginRequest
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class UserRemoteSourceImpl @Inject constructor(
    private val userService: UserService,
): UserRemoteSource{
    override suspend fun googleLogin(socialLoginRequest: SocialLoginRequest): ApiResponse<BaseSuccessResponse<SocialLoginEntity>> {
        return userService.loginGoogle(socialLoginRequest = socialLoginRequest)
    }

    override suspend fun kakaoLogin(accessToken: String): ApiResponse<BaseSuccessResponse<SocialLoginEntity>> {
        return userService.loginKakao(accessToken = accessToken)
    }
}