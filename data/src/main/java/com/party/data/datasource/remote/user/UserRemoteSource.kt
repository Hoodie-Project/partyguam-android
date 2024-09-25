package com.party.data.datasource.remote.user

import com.party.common.ServerApiResponse.BaseSuccessResponse
import com.party.data.entity.user.SocialLoginEntity
import com.skydoves.sandwich.ApiResponse

interface UserRemoteSource {

    // 구글 로그인
    suspend fun googleLogin(accessToken: String): ApiResponse<BaseSuccessResponse<SocialLoginEntity>>

    // 카카오 로그인
    suspend fun kakaoLogin(accessToken: String): ApiResponse<BaseSuccessResponse<SocialLoginEntity>>
}