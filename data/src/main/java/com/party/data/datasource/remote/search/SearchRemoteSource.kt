package com.party.data.datasource.remote.search

import com.party.data.entity.search.SearchDto
import com.skydoves.sandwich.ApiResponse

interface SearchRemoteSource {

    // 검색하기
    suspend fun search(titleSearch: String, page: Int, limit: Int): ApiResponse<SearchDto>
}