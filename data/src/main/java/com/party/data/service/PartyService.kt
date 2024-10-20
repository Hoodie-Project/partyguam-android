package com.party.data.service

import com.party.data.entity.party.PersonalRecruitmentListEntity
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface PartyService {

    @GET("api/parties/recruitments/personalized")
    suspend fun getPersonalizedParties(
        @Query("page") page: Int,
        @Query("limit") size: Int,
        @Query("sort") sort: String = "createdAt",
        @Query("order") order: String,
    ): ApiResponse<PersonalRecruitmentListEntity>
}