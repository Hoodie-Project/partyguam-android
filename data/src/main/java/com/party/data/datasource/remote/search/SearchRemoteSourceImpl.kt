package com.party.data.datasource.remote.search

import com.party.data.dto.search.SearchDto
import com.party.data.service.SearchService
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class SearchRemoteSourceImpl @Inject constructor(
    private val searchService: SearchService
): SearchRemoteSource{
    override suspend fun search(
        titleSearch: String,
        page: Int,
        limit: Int
    ): ApiResponse<SearchDto> {
        return searchService.getSearchedParties(titleSearch = titleSearch, page = page, limit = limit)
    }
}