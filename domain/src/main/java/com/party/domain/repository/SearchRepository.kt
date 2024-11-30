package com.party.domain.repository

import com.party.common.ServerApiResponse
import com.party.domain.model.search.Search

interface SearchRepository {

    // 검색어로 파티 리스트 조회
    suspend fun searchPartiesByKeyword(titleSearch: String, page: Int, limit: Int): ServerApiResponse<Search>
}