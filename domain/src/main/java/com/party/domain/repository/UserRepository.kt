package com.party.domain.repository

import com.party.common.ServerApiResponse
import com.party.domain.model.user.detail.Location
import com.party.domain.model.user.SocialLogin
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.PersonalityList
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.PersonalitySave
import com.party.domain.model.user.detail.PositionList
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.detail.SaveCarrier
import com.party.domain.model.user.detail.SaveInterestLocation
import com.party.domain.model.user.party.MyParty
import com.party.domain.model.user.profile.UserProfile
import com.party.domain.model.user.recruitment.MyRecruitment
import com.party.domain.model.user.signup.UserSignUpRequest
import com.party.domain.model.user.signup.UserSignUp

interface UserRepository {

    // 구글 로그인
    suspend fun googleLogin(accessToken: String): ServerApiResponse<SocialLogin>

    // 카카오 로그인
    suspend fun kakaoLogin(accessToken: String): ServerApiResponse<SocialLogin>

    // 유저 닉네임 중복체크
    suspend fun checkNickName(signupAccessToken: String, nickname: String): ServerApiResponse<String>

    // 유저 회원가입
    suspend fun userSignUp(signupAccessToken: String, userSignUpRequest: UserSignUpRequest): ServerApiResponse<UserSignUp>

    // 특정 지역의 지역 리스트 조회
    suspend fun getLocations(province: String): ServerApiResponse<List<Location>>

    // 관심지역 저장
    suspend fun saveInterestLocation(locations: InterestLocationList): ServerApiResponse<List<SaveInterestLocation>>

    // 특정 직군의 포지션 리스트 조회
    suspend fun getPositions(main: String): ServerApiResponse<List<PositionList>>

    // 유저 경력 저장
    suspend fun saveCarrier(career: SaveCarrierList): ServerApiResponse<List<SaveCarrier>>

    // 유저 경력 삭제
    suspend fun deleteCareer(): ServerApiResponse<Unit>

    // 성향 질문 리스트 전체 조회
    suspend fun getPersonalities(): ServerApiResponse<List<PersonalityList>>

    // 성향 질문 리스트 전체 저장
    suspend fun savePersonalities(personalitySaveRequest: PersonalitySaveRequest): ServerApiResponse<List<PersonalitySave>>

    // 내 파티 리스트 조회
    suspend fun getMyParties(page: Int, limit: Int, sort: String, order: String): ServerApiResponse<MyParty>

    // 내 지원목록 리스트 조회
    suspend fun getMyRecruitments(page: Int, limit: Int, sort: String, order: String): ServerApiResponse<MyRecruitment>

    // 유저의 프로필 조회
    suspend fun getUserProfile(): ServerApiResponse<UserProfile>

}