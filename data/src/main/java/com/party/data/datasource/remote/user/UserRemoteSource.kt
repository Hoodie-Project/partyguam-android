package com.party.data.datasource.remote.user

import com.party.core.domain.DataErrorRemote
import com.party.core.domain.Result
import com.party.data.dto.user.CheckVersionDto
import com.party.data.dto.user.ReportsDto
import com.party.data.dto.user.auth.LinkGoogleDto
import com.party.data.dto.user.auth.LinkKakaoDto
import com.party.data.dto.user.auth.MySocialOauthDto
import com.party.data.dto.user.auth.SocialLoginDto
import com.party.data.dto.user.auth.UserSignUpDto
import com.party.data.dto.user.detail.GetCarrierDto
import com.party.data.dto.user.detail.LocationDto
import com.party.data.dto.user.detail.ModifyCarrierDto
import com.party.data.dto.user.detail.PersonalityListDto
import com.party.data.dto.user.detail.PersonalitySaveDto
import com.party.data.dto.user.detail.PositionListDto
import com.party.data.dto.user.detail.SaveCarrierItemDto
import com.party.data.dto.user.detail.SaveInterestLocationDto
import com.party.data.dto.user.detail.UserLikeLocationDto
import com.party.data.dto.user.notification.NotificationDto
import com.party.data.dto.user.notification.ReadNotificationDto
import com.party.data.dto.user.party.MyPartyDto
import com.party.data.dto.user.profile.UserProfileDto
import com.party.data.dto.user.recruitment.MyRecruitmentDto
import com.party.domain.model.user.AccessTokenRequest
import com.party.domain.model.user.LinkKakaoRequest
import com.party.domain.model.user.ReportsRequest
import com.party.domain.model.user.SaveUserFcmTokenRequest
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.Location
import com.party.domain.model.user.detail.ModifyCarrierList
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.PositionList
import com.party.domain.model.user.detail.SaveCarrier
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.detail.SaveInterestLocation
import com.party.domain.model.user.profile.UserProfileModifyDto
import com.party.domain.model.user.signup.UserSignUpRequest
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UserRemoteSource {

    // 구글 로그인
    suspend fun googleLogin(accessTokenRequest: AccessTokenRequest): ApiResponse<SocialLoginDto>

    // 카카오 로그인
    suspend fun kakaoLogin(accessToken: String): ApiResponse<SocialLoginDto>

    // 나의 소셜 계정 조회
    suspend fun getMySocialOauth(): ApiResponse<List<MySocialOauthDto>>

    // 카카오 계정 연동
    suspend fun linkKakao(linkKakaoRequest: LinkKakaoRequest): ApiResponse<LinkKakaoDto>

    // 구글 계정 연동
    suspend fun linkGoogle(accessTokenRequest: AccessTokenRequest): ApiResponse<LinkGoogleDto>

    // 유저 닉네임 중복체크
    suspend fun checkNickName(signupAccessToken: String, nickname: String): Result<String, DataErrorRemote<String>>

    // 유저 회원가입
    suspend fun userSignUp(signupAccessToken: String, userSignUpRequest: UserSignUpRequest): Result<UserSignUpDto, DataErrorRemote<Unit>>

    // 특정 지역의 지역 리스트 조회
    suspend fun getLocations(province: String): ApiResponse<List<LocationDto>>

    // 특정 지역의 지역 리스트 조회
    suspend fun getLocationsV2(province: String): Result<List<LocationDto>, DataErrorRemote<Unit>>

    // 관심지역 저장
    suspend fun saveInterestLocation(locations: InterestLocationList): ApiResponse<List<SaveInterestLocationDto>>

    // 관심지역 저장
    suspend fun saveInterestLocationV2(locations: InterestLocationList): Result<List<SaveInterestLocationDto>, DataErrorRemote<Unit>>

    // 유저가 지정한 관심지역 조회
    suspend fun getUserLikeLocations(): ApiResponse<List<UserLikeLocationDto>>

    // 특정 직군의 포지션 리스트 조회
    suspend fun getPositions(main: String): ApiResponse<List<PositionListDto>>

    // 특정 직군의 포지션 리스트 조회
    suspend fun getPositionsV2(main: String): Result<List<PositionListDto>, DataErrorRemote<Unit>>

    // 유저 경력 조회
    suspend fun getCareers(): ApiResponse<List<GetCarrierDto>>

    // 유저 경력 저장
    suspend fun saveCarrier(career: SaveCarrierList): ApiResponse<List<SaveCarrierItemDto>>

    // 유저 경력 저장
    suspend fun saveCareerV2(career: SaveCarrierList): Result<List<SaveCarrierItemDto>, DataErrorRemote<Unit>>

    // 유저 경력 수정
    suspend fun modifyCarrier(career: ModifyCarrierList): ApiResponse<ModifyCarrierDto>

    // 유저 경력 전체 삭제
    suspend fun deleteCareer(): ApiResponse<Unit>

    // 성향 질문 리스트 전체 조회
    suspend fun getPersonalities(): ApiResponse<List<PersonalityListDto>>

    // 성향 질문 리스트 전체 저장
    suspend fun savePersonalities(personalitySaveRequest: PersonalitySaveRequest): ApiResponse<List<PersonalitySaveDto>>

    // 내 파티 리스트 조회
    suspend fun getMyParties(page: Int, limit: Int, sort: String, order: String, status: String?): ApiResponse<MyPartyDto>

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

    // 로그아웃
    suspend fun logout(): ApiResponse<Unit>

    // 회원탈퇴
    suspend fun signOut(): ApiResponse<Unit>

    // 신고하기
    suspend fun reports(reportsRequest: ReportsRequest): ApiResponse<ReportsDto>

    // 계정복구하기
    suspend fun recoverAuth(recoverAccessToken: String): ApiResponse<SocialLoginDto>

    // 알림 리스트 조회
    suspend fun getNotifications(
        limit: Int,
        type: String?,
    ): ApiResponse<NotificationDto>

    // 알림 읽음 처리
    suspend fun readNotification(notificationId: Int): ApiResponse<ReadNotificationDto>

    // 알림 삭제처리
    suspend fun deleteNotification(notificationId: Int): ApiResponse<Unit>

    // 유저 FCM 토큰 저장
    suspend fun saveUserFcmToken(saveUserFcmTokenRequest: SaveUserFcmTokenRequest): ApiResponse<Unit>

    // 앱버전 체크
    suspend fun checkVersion(platform: String): ApiResponse<CheckVersionDto>
}