package com.party.domain.repository

import com.party.common.BaseSuccessResponse
import com.party.common.ServerApiResponse
import com.party.domain.model.member.SocialLoginRequest
import com.party.domain.model.member.SocialLoginResponse

interface UserRepository {

    // 구글 로그인
    suspend fun googleLogin(socialLoginRequest: SocialLoginRequest): ServerApiResponse// BaseSuccessResponse<SocialLoginResponse>

    // 카카오 로그인
    suspend fun kakaoLogin(socialLoginRequest: SocialLoginRequest): BaseSuccessResponse<SocialLoginResponse>
}