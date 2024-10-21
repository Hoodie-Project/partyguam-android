package com.party.data.service

import com.party.data.entity.party.PersonalRecruitmentListEntity
import com.party.data.entity.party.RecruitmentListEntity
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
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
}