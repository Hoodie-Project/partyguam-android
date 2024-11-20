package com.party.data.service

import com.party.data.entity.party.PartyDetailDto
import com.party.data.entity.party.PartyListEntity
import com.party.data.entity.party.PersonalRecruitmentListEntity
import com.party.data.entity.party.RecruitmentDetailDto
import com.party.data.entity.party.RecruitmentListEntity
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
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
    ): ApiResponse<PersonalRecruitmentListEntity>

    // 모집 공고 리스트 조회
    @GET("api/parties/recruitments")
    suspend fun getRecruitmentList(
        @Query("page") page: Int,
        @Query("limit") size: Int,
        @Query("sort") sort: String,
        @Query("order") order: String,
    ): ApiResponse<RecruitmentListEntity>

    // 파티 리스트 조회
    @GET("api/parties")
    suspend fun getPartyList(
        @Query("page") page: Int,
        @Query("limit") size: Int,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("partyType") partyTypes: List<Int> // 리스트로 처리
    ): ApiResponse<PartyListEntity>

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
}