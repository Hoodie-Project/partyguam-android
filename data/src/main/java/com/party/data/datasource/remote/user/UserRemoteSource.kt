package com.party.data.datasource.remote.user

import com.party.data.entity.user.SocialLoginEntity
import com.skydoves.sandwich.ApiResponse

interface UserRemoteSource {

    // 구글 로그인
    suspend fun googleLogin(accessToken: String): ApiResponse<SocialLoginEntity>

    // 카카오 로그인
    suspend fun kakaoLogin(accessToken: String): ApiResponse<SocialLoginEntity>

    // 유저 닉네임 중복체크
    suspend fun checkNickName(signupAccessToken: String, nickname: String): ApiResponse<String>
}