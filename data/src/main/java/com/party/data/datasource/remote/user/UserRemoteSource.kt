package com.party.data.datasource.remote.user

import com.party.data.entity.user.detail.LocationEntity
import com.party.data.entity.user.detail.SaveInterestLocationEntity
import com.party.data.entity.user.auth.SocialLoginEntity
import com.party.data.entity.user.auth.UserSignUpEntity
import com.party.data.entity.user.detail.PersonalityListEntity
import com.party.data.entity.user.detail.PositionListEntity
import com.party.data.entity.user.detail.SaveCarrierEntity
import com.party.data.entity.user.detail.SaveCarrierEntity1
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.SaveCarrierList
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

    // 특정 직군의 포지션 리스트 조회
    suspend fun getPositions(accessToken: String, main: String): ApiResponse<List<PositionListEntity>>

    // 유저 경력 저장
    suspend fun saveCarrier(accessToken: String, career: SaveCarrierList): ApiResponse<SaveCarrierEntity1>

    // 성향 질문 리스트 전체 조회
    suspend fun getPersonalities(accessToken: String): ApiResponse<List<PersonalityListEntity>>
}