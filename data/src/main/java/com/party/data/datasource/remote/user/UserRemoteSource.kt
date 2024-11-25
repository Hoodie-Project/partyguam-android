package com.party.data.datasource.remote.user

import com.party.data.entity.user.auth.SocialLoginDto
import com.party.data.entity.user.auth.UserSignUpDto
import com.party.data.entity.user.detail.LocationDto
import com.party.data.entity.user.detail.PersonalityListDto
import com.party.data.entity.user.detail.PersonalitySaveDto
import com.party.data.entity.user.detail.PositionListDto
import com.party.data.entity.user.detail.SaveCarrierDto
import com.party.data.entity.user.detail.SaveInterestLocationDto
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.signup.UserSignUpRequest
import com.skydoves.sandwich.ApiResponse

interface UserRemoteSource {

    // 구글 로그인
    suspend fun googleLogin(accessToken: String): ApiResponse<SocialLoginDto>

    // 카카오 로그인
    suspend fun kakaoLogin(accessToken: String): ApiResponse<SocialLoginDto>

    // 유저 닉네임 중복체크
    suspend fun checkNickName(signupAccessToken: String, nickname: String): ApiResponse<String>

    // 유저 회원가입
    suspend fun userSignUp(signupAccessToken: String, userSignUpRequest: UserSignUpRequest): ApiResponse<UserSignUpDto>

    // 특정 지역의 지역 리스트 조회
    suspend fun getLocations(province: String): ApiResponse<List<LocationDto>>

    // 관심지역 저장
    suspend fun saveInterestLocation(locations: InterestLocationList): ApiResponse<List<SaveInterestLocationDto>>

    // 특정 직군의 포지션 리스트 조회
    suspend fun getPositions(main: String): ApiResponse<List<PositionListDto>>

    // 유저 경력 저장
    suspend fun saveCarrier(career: SaveCarrierList): ApiResponse<SaveCarrierDto>

    // 성향 질문 리스트 전체 조회
    suspend fun getPersonalities(): ApiResponse<List<PersonalityListDto>>

    // 성향 질문 리스트 전체 저장
    suspend fun savePersonalities(personalitySaveRequest: PersonalitySaveRequest): ApiResponse<List<PersonalitySaveDto>>
}