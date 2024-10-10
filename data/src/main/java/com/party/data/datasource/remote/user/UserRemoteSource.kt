package com.party.data.datasource.remote.user

import com.party.data.entity.user.LocationEntity
import com.party.data.entity.user.SaveInterestLocationEntity
import com.party.data.entity.user.auth.SocialLoginEntity
import com.party.data.entity.user.auth.UserSignUpEntity
import com.party.domain.model.user.detail.InterestLocationList
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
    suspend fun getLocations(accessToken: String, province: String): ApiResponse<List<LocationEntity>>

    // 관심지역 저장
    suspend fun saveInterestLocation(accessToken: String, locations: InterestLocationList): ApiResponse<List<SaveInterestLocationEntity>>
}