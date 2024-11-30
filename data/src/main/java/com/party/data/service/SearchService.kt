package com.party.data.service

import com.party.data.entity.search.SearchDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    // 검색하기
    @GET("api/parties/search")
    suspend fun getSearchedParties(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("titleSearch") titleSearch: String,
    ): ApiResponse<SearchDto>
}