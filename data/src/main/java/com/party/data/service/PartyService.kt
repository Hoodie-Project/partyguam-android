package com.party.data.service

import com.party.data.entity.party.PartyAuthorityDto
import com.party.data.entity.party.PartyCreateDto
import com.party.data.entity.party.PartyDetailDto
import com.party.data.entity.party.PartyListDto
import com.party.data.entity.party.PartyRecruitmentDto
import com.party.data.entity.party.PartyUsersDto
import com.party.data.entity.party.PersonalRecruitmentListDto
import com.party.data.entity.party.RecruitmentDetailDto
import com.party.data.entity.party.RecruitmentListDto
import com.party.domain.model.party.PartyCreateRequest
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
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
    ): ApiResponse<RecruitmentListDto>

    // 파티 리스트 조회
    @GET("api/parties")
    suspend fun getPartyList(
        @Query("page") page: Int,
        @Query("limit") size: Int,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("partyType") partyTypes: List<Int> // 리스트로 처리
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
    ): ApiResponse<List<PartyRecruitmentDto>>

    // 파티 상세 조회 - 나의 파티 권한 조회
    @GET("api/parties/{partyId}/users/me/authority")
    suspend fun getPartyAuthority(
        @Path(value = "partyId") partyId: Int,
    ): ApiResponse<PartyAuthorityDto>

    // 파티 생성
    @POST("api/parties")
    suspend fun saveParty(
        @Body partyCreateRequest: PartyCreateRequest
    ): ApiResponse<PartyCreateDto>
}