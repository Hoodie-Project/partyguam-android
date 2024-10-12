package com.party.domain.repository

import com.party.common.ServerApiResponse
import com.party.domain.model.user.detail.LocationResponse
import com.party.domain.model.user.SocialLoginResponse
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.PersonalityListResponse
import com.party.domain.model.user.detail.PositionListResponse
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.detail.SaveCarrierResponse
import com.party.domain.model.user.detail.SaveInterestLocationResponse
import com.party.domain.model.user.signup.UserSignUpRequest
import com.party.domain.model.user.signup.UserSignUpResponse

interface UserRepository {

    // 구글 로그인
    suspend fun googleLogin(accessToken: String): ServerApiResponse<SocialLoginResponse>

    // 카카오 로그인
    suspend fun kakaoLogin(accessToken: String): ServerApiResponse<SocialLoginResponse>

    // 유저 닉네임 중복체크
    suspend fun checkNickName(signupAccessToken: String, nickname: String): ServerApiResponse<String>

    // 유저 회원가입
    suspend fun userSignUp(signupAccessToken: String, userSignUpRequest: UserSignUpRequest): ServerApiResponse<UserSignUpResponse>

    // 특정 지역의 지역 리스트 조회
    suspend fun getLocations(accessToken: String, province: String): ServerApiResponse<List<LocationResponse>>

    // 관심지역 저장
    suspend fun saveInterestLocation(accessToken: String, locations: InterestLocationList): ServerApiResponse<List<SaveInterestLocationResponse>>

    // 특정 직군의 포지션 리스트 조회
    suspend fun getPositions(accessToken: String, main: String): ServerApiResponse<List<PositionListResponse>>

    // 유저 경력 저장
    suspend fun saveCarrier(accessToken: String, career: SaveCarrierList): ServerApiResponse<List<SaveCarrierResponse>>

    // 성향 질문 리스트 전체 조회
    suspend fun getPersonalities(accessToken: String): ServerApiResponse<List<PersonalityListResponse>>

}