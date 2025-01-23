package com.party.data.service

import com.party.data.dto.user.auth.LinkKakaoDto
import com.party.data.dto.user.auth.MySocialOauthDto
import com.party.data.dto.user.detail.LocationDto
import com.party.data.dto.user.detail.PersonalityListDto
import com.party.data.dto.user.detail.PersonalitySaveDto
import com.party.data.dto.user.detail.PositionListDto
import com.party.data.dto.user.detail.SaveCarrierDto
import com.party.data.dto.user.detail.SaveInterestLocationDto
import com.party.data.dto.user.party.MyPartyDto
import com.party.data.dto.user.profile.UserProfileDto
import com.party.data.dto.user.recruitment.MyRecruitmentDto
import com.party.domain.model.user.LinkKakaoRequest
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.profile.UserProfileModifyDto
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
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

    // 관심지역 저장
    @POST("api/users/me/locations")
    suspend fun saveInterestLocation(
        @Body locations: InterestLocationList,
    ): ApiResponse<List<SaveInterestLocationDto>>

    // 특정 직군의 포지션 리스트 조회
    @GET("api/positions")
    suspend fun getPositions(
        @Query("main") main: String,
    ): ApiResponse<List<PositionListDto>>

    // 유저 경력 저장
    @POST("api/users/me/careers")
    suspend fun saveCareer(
        @Body career: SaveCarrierList,
    ): ApiResponse<SaveCarrierDto>

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
}