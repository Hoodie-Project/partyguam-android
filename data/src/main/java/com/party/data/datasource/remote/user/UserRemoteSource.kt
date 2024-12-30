package com.party.data.datasource.remote.user

import com.party.data.dto.user.auth.SocialLoginDto
import com.party.data.dto.user.auth.UserSignUpDto
import com.party.data.dto.user.detail.LocationDto
import com.party.data.dto.user.detail.PersonalityListDto
import com.party.data.dto.user.detail.PersonalitySaveDto
import com.party.data.dto.user.detail.PositionListDto
import com.party.data.dto.user.detail.SaveCarrierDto
import com.party.data.dto.user.detail.SaveInterestLocationDto
import com.party.data.dto.user.party.MyPartyDto
import com.party.data.dto.user.profile.UserProfileDto
import com.party.data.dto.user.recruitment.MyRecruitmentDto
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.profile.UserProfileModifyDto
import com.party.domain.model.user.signup.UserSignUpRequest
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

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

    // 유저 경력 전체 삭제
    suspend fun deleteCareer(): ApiResponse<Unit>

    // 성향 질문 리스트 전체 조회
    suspend fun getPersonalities(): ApiResponse<List<PersonalityListDto>>

    // 성향 질문 리스트 전체 저장
    suspend fun savePersonalities(personalitySaveRequest: PersonalitySaveRequest): ApiResponse<List<PersonalitySaveDto>>

    // 내 파티 리스트 조회
    suspend fun getMyParties(page: Int, limit: Int, sort: String, order: String): ApiResponse<MyPartyDto>

    // 내 모집 리스트 조회
    suspend fun getMyRecruitments(page: Int, limit: Int, sort: String, order: String): ApiResponse<MyRecruitmentDto>

    // 유저의 프로필 조회
    suspend fun getUserProfile(): ApiResponse<UserProfileDto>

    // 유저의 프로필 수정
    suspend fun updateUserProfile(
        image: MultipartBody.Part?,
        genderVisible: Boolean?,
        birthVisible: Boolean?,
        portfolioTitle: RequestBody?,
        portfolio: RequestBody?,
    ): ApiResponse<UserProfileModifyDto>

    // 질문에 대한 저장된 응답 전체 삭제
    suspend fun deletePersonalities(personalityQuestionId: Int): ApiResponse<Unit>

    // 관심지역 전체 삭제
    suspend fun deleteInterestLocation(): ApiResponse<Unit>
}