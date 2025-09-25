package com.party.data.service

import com.party.data.dto.user.ReportsDto
import com.party.data.dto.user.auth.LinkGoogleDto
import com.party.data.dto.user.auth.LinkKakaoDto
import com.party.data.dto.user.auth.MySocialOauthDto
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
import com.party.domain.model.user.detail.ModifyCarrierList
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.profile.UserProfileModifyDto
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    // 특정 지역의 지역 리스트 조회
    @GET("api/locations")
    suspend fun getLocations(
        @Query("province") province: String,
    ): ApiResponse<List<LocationDto>>

    // 특정 지역의 지역 리스트 조회
    @GET("api/locations")
    suspend fun getLocationsV2(
        @Query("province") province: String,
    ): Response<List<LocationDto>>

    // 관심지역 저장
    @POST("api/users/me/locations")
    suspend fun saveInterestLocation(
        @Body locations: InterestLocationList,
    ): ApiResponse<List<SaveInterestLocationDto>>

    // 관심지역 저장
    @POST("api/users/me/locations")
    suspend fun saveInterestLocationV2(
        @Body locations: InterestLocationList,
    ): Response<List<SaveInterestLocationDto>>

    // 유저가 지정한 관심지역 조회
    @GET("api/users/me/locations")
    suspend fun getUserLikeLocations(): ApiResponse<List<UserLikeLocationDto>>

    // 특정 직군의 포지션 리스트 조회
    @GET("api/positions")
    suspend fun getPositions(
        @Query("main") main: String,
    ): ApiResponse<List<PositionListDto>>

    // 특정 직군의 포지션 리스트 조회
    @GET("api/positions")
    suspend fun getPositionsV2(
        @Query("main") main: String,
    ): Response<List<PositionListDto>>

    // 유저 경력 조회
    @GET("api/users/me/careers")
    suspend fun getCareer(): ApiResponse<List<GetCarrierDto>>

    // 유저 경력 저장
    @POST("api/users/me/careers")
    suspend fun saveCareer(
        @Body career: SaveCarrierList,
    ): ApiResponse<List<SaveCarrierItemDto>>

    // 유저 경력 저장
    @POST("api/users/me/careers")
    suspend fun saveCareerV2(
        @Body career: SaveCarrierList,
    ): Response<List<SaveCarrierItemDto>>

    // 유저 경력 수정
    @PATCH("api/users/me/careers")
    suspend fun modifyCareer(
        @Body career: ModifyCarrierList
    ): ApiResponse<ModifyCarrierDto>

    // 유저 경력 삭제
    @DELETE("api/users/me/careers")
    suspend fun deleteCareer(): ApiResponse<Unit>

    // 성향 질문 리스트 전체 조회
    @GET("api/personalities")
    suspend fun getPersonalities(): ApiResponse<List<PersonalityListDto>>

    // 성향 질문 리스트 전체 저장
    @POST("api/users/me/personalities")
    suspend fun savePersonalities(
        @Body personalitySaveRequest: PersonalitySaveRequest,
    ): ApiResponse<List<PersonalitySaveDto>>

    // 내 파티 리스트 조회
    @GET("api/users/me/parties")
    suspend fun getMyParties(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("status") status: String? = null,
    ): ApiResponse<MyPartyDto>

    // 내 지원목록 리스트 조회
    @GET("api/users/me/parties/applications")
    suspend fun getMyRecruitments(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String,
        @Query("order") order: String,
    ): ApiResponse<MyRecruitmentDto>

    // 유저의 프로필 조회
    @GET("api/users/me")
    suspend fun getUserProfile(): ApiResponse<UserProfileDto>

    // 유저의 프로필 수정
    @Multipart
    @PATCH("api/users/me")
    suspend fun updateUserProfile(
        @Part image: MultipartBody.Part? = null,
        @Part("genderVisible") genderVisible: Boolean? = null,
        @Part("birthVisible") birthVisible: Boolean? = null,
        @Part("portfolioTitle") portfolioTitle: RequestBody? = null,
        @Part("portfolio") portfolio: RequestBody? = null,
    ): ApiResponse<UserProfileModifyDto>

    // 질문에 대한 저장된 응답 전체 삭제
    @DELETE("api/users/me/personalities/questions/{personalityQuestionId}")
    suspend fun deletePersonalities(
        @Path("personalityQuestionId") personalityQuestionId: Int,
    ): ApiResponse<Unit>

    // 관심지역 전체 삭제
    @DELETE("api/users/me/locations")
    suspend fun deleteInterestLocations(): ApiResponse<Unit>

    // 로그아웃
    @DELETE("api/users/logout")
    suspend fun logout(): ApiResponse<Unit>

    // 회원탈퇴
    @DELETE("api/users/signout")
    suspend fun signOut(): ApiResponse<Unit>

    // 나의 소셜 계정 조회
    @GET("api/users/me/oauth")
    suspend fun getMySocialOauth(): ApiResponse<List<MySocialOauthDto>>

    // 카카오 계정 연동
    @POST("api/users/kakao/app/link")
    suspend fun linkKakao(
        @Body linkKakaoRequest: LinkKakaoRequest
    ): ApiResponse<LinkKakaoDto>

    // 구글 계정 연동
    @POST("api/users/google/app/link")
    suspend fun linkGoogle(
        @Body accessTokenRequest: AccessTokenRequest
    ): ApiResponse<LinkGoogleDto>

    // 신고하기
    @POST("api/reports")
    suspend fun reports(
        @Body reportsRequest: ReportsRequest
    ): ApiResponse<ReportsDto>

    // 알림 리스트 조회
    @GET("api/notifications")
    suspend fun getNotifications(
        @Query("limit") limit: Int,
        @Query("type") type: String? = null
    ): ApiResponse<NotificationDto>

    // 알림 읽음 처리
    @PATCH("api/notifications/{notificationId}/read")
    suspend fun readNotification(
        @Path("notificationId") notificationId: Int
    ): ApiResponse<ReadNotificationDto>

    // 알림 삭제 처리
    @DELETE("api/notifications/{notificationId}")
    suspend fun deleteNotification(
        @Path("notificationId") notificationId: Int
    ): ApiResponse<Unit>

    // 유저 FCM 토큰 저장
    @POST("api/fcm/register-token")
    suspend fun saveUserFcmToken(
        @Body saveUserFcmTokenRequest: SaveUserFcmTokenRequest
    ): ApiResponse<Unit>
}