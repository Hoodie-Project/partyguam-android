package com.party.data.service

import com.party.data.dto.party.ApprovalAndRejectionDto
import com.party.data.dto.party.CheckUserApplicationStatusDto
import com.party.data.dto.party.PartyApplyDto
import com.party.data.dto.party.PartyAuthorityDto
import com.party.data.dto.party.PartyCreateDto
import com.party.data.dto.party.PartyDetailDto
import com.party.data.dto.party.PartyListDto
import com.party.data.dto.party.PartyMembersInfoDto
import com.party.data.dto.party.PartyModifyDto
import com.party.data.dto.party.PartyRecruitmentCompletedDto
import com.party.data.dto.party.PartyRecruitmentDto
import com.party.data.dto.party.PartyUsersDto
import com.party.data.dto.party.PersonalRecruitmentListDto
import com.party.data.dto.party.RecruitmentApplicantDto
import com.party.data.dto.party.RecruitmentCreateDto
import com.party.data.dto.party.RecruitmentDetailDto
import com.party.data.dto.party.RecruitmentListDto
import com.party.domain.model.party.DelegatePartyMasterRequest
import com.party.domain.model.party.ModifyPartyUserPositionRequest
import com.party.domain.model.party.ModifyRecruitmentRequest
import com.party.domain.model.party.PartyApplyRequest
import com.party.domain.model.party.RecruitmentCreateRequest
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PartyService {

    // 개인 맞춤 모집공고
    @GET("api/parties/recruitments/personalized")
    suspend fun getPersonalizedParties(
        @Query("page") page: Int,
        @Query("limit") size: Int,
        @Query("sort") sort: String,
        @Query("order") order: String,
    ): ApiResponse<PersonalRecruitmentListDto>

    // 모집 공고 리스트 조회
    @GET("api/parties/recruitments")
    suspend fun getRecruitmentList(
        @Query("page") page: Int,
        @Query("limit") size: Int,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("titleSearch") titleSearch: String? = null,
        @Query("partyType") partyTypes: List<Int>,
        @Query("position") position: List<Int>,
    ): ApiResponse<RecruitmentListDto>

    // 파티 리스트 조회
    @GET("api/parties")
    suspend fun getPartyList(
        @Query("page") page: Int,
        @Query("limit") size: Int,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("partyType") partyTypes: List<Int>,
        @Query("status") status: String? = null,
        @Query("titleSearch") titleSearch: String? = null,
    ): ApiResponse<PartyListDto>

    // 모집공고 상세 조회
    @GET("api/parties/recruitments/{partyRecruitmentId}")
    suspend fun getRecruitmentDetail(
        @Path(value = "partyRecruitmentId") partyRecruitmentId: Int,
    ): ApiResponse<RecruitmentDetailDto>

    // 파티 상세 조회
    @GET("api/parties/{partyId}")
    suspend fun getPartyDetail(
        @Path(value = "partyId") partyId: Int,
    ): ApiResponse<PartyDetailDto>

    // 파티 상세 조회 - 파티원 조회
    @GET("api/parties/{partyId}/users")
    suspend fun getPartyUsers(
        @Path(value = "partyId") partyId: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String,
        @Query("order") order: String,
    ): ApiResponse<PartyUsersDto>

    // 파티 상세 조회 - 모집 공고 리스트 조회
    @GET("api/parties/{partyId}/recruitments")
    suspend fun getPartyRecruitmentList(
        @Path(value = "partyId") partyId: Int,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("main") main: String? = null,
        @Query("status") status: String? = null,
    ): ApiResponse<List<PartyRecruitmentDto>>

    // 파티 상세 조회 - 나의 파티 권한 조회
    @GET("api/parties/{partyId}/users/me/authority")
    suspend fun getPartyAuthority(
        @Path(value = "partyId") partyId: Int,
    ): ApiResponse<PartyAuthorityDto>

    // 파티 생성
    @Multipart
    @POST("api/parties")
    suspend fun saveParty(
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part("partyTypeId") partyTypeId: RequestBody,
        @Part("positionId") positionId: RequestBody,
        @Part image: MultipartBody.Part,
    ): ApiResponse<PartyCreateDto>

    // 파티 수정
    @Multipart
    @PATCH("api/parties/{partyId}/admin")
    suspend fun modifyParty(
        @Path(value = "partyId") partyId: Int,
        @Part("title") title: RequestBody? = null,
        @Part("content") content: RequestBody? = null,
        @Part("partyTypeId") partyTypeId: RequestBody? = null,
        @Part image: MultipartBody.Part? = null,
        @Part("status") status: RequestBody? = null,
    ): ApiResponse<PartyModifyDto>


    // 파티지원하기 - 모집공고 지원
    @POST("api/parties/{partyId}/recruitments/{partyRecruitmentId}/applications")
    suspend fun applyPartyRecruitment(
        @Path(value = "partyId") partyId: Int,
        @Path(value = "partyRecruitmentId") partyRecruitmentId: Int,
        @Body partyApplyRequest: PartyApplyRequest,
    ): ApiResponse<PartyApplyDto>

    // 모집공고 추가하기
    @POST("api/parties/{partyId}/recruitments")
    suspend fun saveRecruitment(
        @Path(value = "partyId") partyId: Int,
        @Body recruitmentCreateRequest: RecruitmentCreateRequest,
    ): ApiResponse<RecruitmentCreateDto>

    // 관리자 - 파티원 리스트 조회
    @GET("api/parties/{partyId}/admin/users")
    suspend fun getPartyMembers(
        @Path(value = "partyId") partyId: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("main") main: String? = null,
        @Query("nickname") nickname: String? = null,
    ): ApiResponse<PartyMembersInfoDto>

    // 관리자 - 파티원 포지션 변경
    @PATCH("api/parties/{partyId}/admin/users/{partyUserId}")
    suspend fun modifyPartyMemberPosition(
        @Path(value = "partyId") partyId: Int,
        @Path(value = "partyUserId") partyUserId: Int,
        @Body modifyPartyUserPositionRequest: ModifyPartyUserPositionRequest,
    ): ApiResponse<Unit>

    // 관리자 - 파티 삭제
    @DELETE("api/parties/{partyId}/admin")
    suspend fun deleteParty(
        @Path(value = "partyId") partyId: Int,
    ): ApiResponse<Unit>

    // 관리자 - 파티원 내보내기
    @DELETE("api/parties/{partyId}/admin/users/{partyUserId}")
    suspend fun deletePartyMember(
        @Path(value = "partyId") partyId: Int,
        @Path(value = "partyUserId") partyUserId: Int,
    ): ApiResponse<Unit>

    // 관리자 - 파티장 위임하기
    @PATCH("api/parties/{partyId}/admin/delegation")
    suspend fun changeMaster(
        @Path(value = "partyId") partyId: Int,
        @Body delegatePartyMasterRequest: DelegatePartyMasterRequest,
    ): ApiResponse<Unit>

    // 관리자 - 파티모집 삭제
    @DELETE("api/parties/{partyId}/admin/recruitments/{partyRecruitmentId}")
    suspend fun deleteRecruitment(
        @Path(value = "partyId") partyId: Int,
        @Path(value = "partyRecruitmentId") partyRecruitmentId: Int,
    ): ApiResponse<Unit>

    // 관리자 - 파티모집 수정
    @PATCH("api/parties/{partyId}/admin/recruitments/{partyRecruitmentId}")
    suspend fun modifyRecruitment(
        @Path(value = "partyId") partyId: Int,
        @Path(value = "partyRecruitmentId") partyRecruitmentId: Int,
        @Body modifyRecruitmentRequest: ModifyRecruitmentRequest,
    ): ApiResponse<Unit>

    // 관리자 - 파티모집별 지원자 조회
    @GET("api/parties/{partyId}/recruitments/{partyRecruitmentId}/applications")
    suspend fun getRecruitmentApplicants(
        @Path(value = "partyId") partyId: Int,
        @Path(value = "partyRecruitmentId") partyRecruitmentId: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String,
        @Query("order") order: String,
    ): ApiResponse<RecruitmentApplicantDto>

    // 파티장이 지원자 수락
    @POST("api/parties/{partyId}/admin/applications/{partyApplicationId}/approval")
    suspend fun acceptApplicant(
        @Path(value = "partyId") partyId: Int,
        @Path(value = "partyApplicationId") partyApplicationId: Int,
    ): ApiResponse<ApprovalAndRejectionDto>

    // 파티장이 지원자 거절
    @POST("api/parties/{partyId}/admin/applications/{partyApplicationId}/rejection")
    suspend fun rejectApplicant(
        @Path(value = "partyId") partyId: Int,
        @Path(value = "partyApplicationId") partyApplicationId: Int,
    ): ApiResponse<ApprovalAndRejectionDto>

    // 지원한 모집공고 취소하기(검토중일때)
    @DELETE("api/parties/{partyId}/applications/{partyApplicationId}")
    suspend fun cancelRecruitment(
        @Path(value = "partyId") partyId: Int,
        @Path(value = "partyApplicationId") partyApplicationId: Int,
    ): ApiResponse<Unit>

    // 모집공고에 유저가 지원했는지 여부
    @GET("api/parties/{partyId}/recruitments/{partyRecruitmentId}/applications/me")
    suspend fun checkUserApplicationStatus(
        @Path(value = "partyId") partyId: Int,
        @Path(value = "partyRecruitmentId") partyRecruitmentId: Int,
    ): ApiResponse<CheckUserApplicationStatusDto>

    // 파티 나가기 (파티유저가)
    @DELETE("api/parties/{partyId}/users/me")
    suspend fun exitParty(
        @Path(value = "partyId") partyId: Int,
    ): ApiResponse<Unit>

    // 파티 모집 완료 처리
    @PATCH("api/parties/{partyId}/admin/recruitment/{partyRecruitmentId}/completed")
    suspend fun completePartyRecruitment(
        @Path(value = "partyId") partyId: Int,
        @Path(value = "partyRecruitmentId") partyRecruitmentId: Int,
    ): ApiResponse<PartyRecruitmentCompletedDto>

    // 유저가 파티 합류 최종 수락
    @POST("api/parties/{partyId}/applications/{partyApplicationId}/approval")
    suspend fun approvalParty(
        @Path(value = "partyId") partyId: Int,
        @Path(value = "partyApplicationId") partyApplicationId: Int,
    ): ApiResponse<ApprovalAndRejectionDto>

    // 유저가 파티 합류 최종 거절
    @POST("api/parties/{partyId}/applications/{partyApplicationId}/rejection")
    suspend fun rejectionParty(
        @Path(value = "partyId") partyId: Int,
        @Path(value = "partyApplicationId") partyApplicationId: Int,
    ): ApiResponse<ApprovalAndRejectionDto>

}