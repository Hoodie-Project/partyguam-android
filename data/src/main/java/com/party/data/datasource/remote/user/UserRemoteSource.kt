package com.party.data.datasource.remote.user

import com.party.data.entity.user.LocationEntity
import com.party.data.entity.user.SocialLoginEntity
import com.party.data.entity.user.UserSignUpEntity
import com.party.domain.model.user.signup.UserSignUpRequest
import com.skydoves.sandwich.ApiResponse

interface UserRemoteSource {

    // 구글 로그인
    suspend fun googleLogin(accessToken: String): ApiResponse<SocialLoginEntity>

    // 카카오 로그인
    suspend fun kakaoLogin(accessToken: String): ApiResponse<SocialLoginEntity>

    // 유저 닉네임 중복체크
    suspend fun checkNickName(signupAccessToken: String, nickname: String): ApiResponse<String>

    // 유저 회원가입
    suspend fun userSignUp(signupAccessToken: String, userSignUpRequest: UserSignUpRequest): ApiResponse<UserSignUpEntity>

    // 특정 지역의 지역 리스트 조회
    suspend fun getLocations(province: String): ApiResponse<List<LocationEntity>>
}