package com.party.domain.repository

import com.party.common.ServerApiResponse
import com.party.core.domain.DataError
import com.party.core.domain.DataErrorRemote
import com.party.core.domain.Result
import com.party.domain.model.user.AccessTokenRequest
import com.party.domain.model.user.CheckVersion
import com.party.domain.model.user.LinkGoogle
import com.party.domain.model.user.LinkKakao
import com.party.domain.model.user.LinkKakaoRequest
import com.party.domain.model.user.MySocialOauth
import com.party.domain.model.user.notification.Notification
import com.party.domain.model.user.Reports
import com.party.domain.model.user.ReportsRequest
import com.party.domain.model.user.SaveUserFcmTokenRequest
import com.party.domain.model.user.SocialLogin
import com.party.domain.model.user.detail.GetCarrier
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.Location
import com.party.domain.model.user.detail.ModifyCarrier
import com.party.domain.model.user.detail.ModifyCarrierList
import com.party.domain.model.user.detail.PersonalityList
import com.party.domain.model.user.detail.PersonalitySave
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.PositionList
import com.party.domain.model.user.detail.SaveCarrier
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.detail.SaveInterestLocation
import com.party.domain.model.user.detail.UserLikeLocation
import com.party.domain.model.user.notification.ReadNotification
import com.party.domain.model.user.party.MyParty
import com.party.domain.model.user.profile.UserLocation
import com.party.domain.model.user.profile.UserProfile
import com.party.domain.model.user.profile.UserProfileModify
import com.party.domain.model.user.recruitment.MyRecruitment
import com.party.domain.model.user.signup.UserSignUp
import com.party.domain.model.user.signup.UserSignUpRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UserRepository {

    // 구글 로그인
    suspend fun googleLogin(accessTokenRequest: AccessTokenRequest): ServerApiResponse<SocialLogin>

    // 카카오 로그인
    suspend fun kakaoLogin(accessToken: String): ServerApiResponse<SocialLogin>

    // 나의 소셜 계정 조회
    suspend fun getMySocialOauth(): ServerApiResponse<List<MySocialOauth>>

    // 카카오 계정 연동
    suspend fun linkKakao(linkKakaoRequest: LinkKakaoRequest): ServerApiResponse<LinkKakao>

    // 구글 계정 연동
    suspend fun linkGoogle(accessTokenRequest: AccessTokenRequest): ServerApiResponse<LinkGoogle>

    // 유저 닉네임 중복체크
    suspend fun checkNickName(signupAccessToken: String, nickname: String): Result<String, DataErrorRemote<String>>

    // 유저 회원가입
    suspend fun userSignUp(signupAccessToken: String, userSignUpRequest: UserSignUpRequest): Result<UserSignUp, DataErrorRemote<Unit>>

    // 특정 지역의 지역 리스트 조회
    suspend fun getLocations(province: String): ServerApiResponse<List<Location>>

    // 특정 지역의 지역 리스트 조회
    suspend fun getLocationsV2(province: String): Result<List<Location>, DataErrorRemote<Unit>>

    // 관심지역 저장
    suspend fun saveInterestLocation(locations: InterestLocationList): ServerApiResponse<List<SaveInterestLocation>>

    // 관심지역 저장
    suspend fun saveInterestLocationV2(locations: InterestLocationList): Result<List<SaveInterestLocation>, DataErrorRemote<Unit>>

    // 유저가 지정한 관심지역 조회
    suspend fun getUserLikeLocations(): ServerApiResponse<List<UserLikeLocation>>

    // 특정 직군의 포지션 리스트 조회
    suspend fun getPositions(main: String): ServerApiResponse<List<PositionList>>

    // 특정 직군의 포지션 리스트 조회
    suspend fun getPositionsV2(main: String): Result<List<PositionList>, DataErrorRemote<Unit>>

    // 유저 경력 조회
    suspend fun getCareer(): ServerApiResponse<List<GetCarrier>>

    // 유저 경력 저장
    suspend fun saveCarrier(career: SaveCarrierList): ServerApiResponse<List<SaveCarrier>>

    // 유저 경력 저장
    suspend fun saveCareerV2(career: SaveCarrierList): Result<List<SaveCarrier>, DataErrorRemote<Unit>>

    // 유저 경력 수정
    suspend fun modifyCarrier(career: ModifyCarrierList): ServerApiResponse<List<ModifyCarrier>>

    // 유저 경력 삭제
    suspend fun deleteCareer(): ServerApiResponse<Unit>

    // 성향 질문 리스트 전체 조회
    suspend fun getPersonalities(): ServerApiResponse<List<PersonalityList>>

    // 성향 질문 리스트 전체 조회
    suspend fun getPersonalitiesV2(): Result<List<PersonalityList>, DataErrorRemote<Unit>>

    // 성향 질문 리스트 전체 저장
    suspend fun savePersonalities(personalitySaveRequest: PersonalitySaveRequest): ServerApiResponse<List<PersonalitySave>>

    // 성향 질문 리스트 전체 저장
    suspend fun savePersonalitiesV2(personalitySaveRequest: PersonalitySaveRequest): Result<List<PersonalitySave>, DataErrorRemote<Unit>>

    // 내 파티 리스트 조회
    suspend fun getMyParties(page: Int, limit: Int, sort: String, order: String, status: String?): ServerApiResponse<MyParty>

    // 내 지원목록 리스트 조회
    suspend fun getMyRecruitments(page: Int, limit: Int, sort: String, order: String): ServerApiResponse<MyRecruitment>

    // 유저의 프로필 조회
    suspend fun getUserProfile(): ServerApiResponse<UserProfile>

    // 유저의 프로필 수정
    suspend fun updateUserProfile(
        image: MultipartBody.Part?,
        genderVisible: Boolean?,
        birthVisible: Boolean?,
        portfolioTitle: RequestBody?,
        portfolio: RequestBody?,
    ): ServerApiResponse<UserProfileModify>

    // 질문에 대한 저장된 응답 전체 삭제
    suspend fun deletePersonalities(personalityQuestionId: Int): ServerApiResponse<Unit>

    // 관심지역 전체 삭제
    suspend fun deleteInterestLocation(): ServerApiResponse<Unit>

    // 관심지역 전체 삭제
    suspend fun deleteInterestLocationV2(): Result<Unit, DataErrorRemote<Unit>>

    // 로그아웃
    suspend fun logout(): ServerApiResponse<Unit>

    // 회원탈퇴
    suspend fun signOut(): ServerApiResponse<Unit>

    // 신고하기
    suspend fun reports(reportsRequest: ReportsRequest): ServerApiResponse<Reports>

    // 계정복구하기
    suspend fun recoverAuth(recoverAccessToken: String): ServerApiResponse<SocialLogin>

    // 알림 리스트 조회
    suspend fun getNotifications(
        limit: Int,
        type: String?
    ): ServerApiResponse<Notification>

    // 알림 읽음 처리
    suspend fun readNotification(notificationId: Int): ServerApiResponse<ReadNotification>

    // 알림 삭제 처리
    suspend fun deleteNotification(notificationId: Int): ServerApiResponse<Unit>

    // 유저 FCM 토큰 저장
    suspend fun saveUserFcmToken(saveUserFcmTokenRequest: SaveUserFcmTokenRequest): ServerApiResponse<Unit>

    // 앱버전 체크
    suspend fun checkVersion(platform: String): ServerApiResponse<CheckVersion>
}