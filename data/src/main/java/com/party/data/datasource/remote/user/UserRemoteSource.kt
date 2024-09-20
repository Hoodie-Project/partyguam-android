package com.party.data.datasource.remote.user

import com.party.common.BaseSuccessResponse
import com.party.data.entity.user.SocialLoginEntity
import com.party.domain.model.member.SocialLoginRequest
import com.skydoves.sandwich.ApiResponse

interface UserRemoteSource {

    // 구글 로그인
    suspend fun googleLogin(socialLoginRequest: SocialLoginRequest): ApiResponse<BaseSuccessResponse<SocialLoginEntity>>

    // 카카오 로그인
    suspend fun kakaoLogin(socialLoginRequest: SocialLoginRequest): ApiResponse<BaseSuccessResponse<SocialLoginEntity>>
}