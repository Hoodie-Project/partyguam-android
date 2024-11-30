package com.party.data.repository

import com.party.common.ServerApiResponse
import com.party.common.ServerApiResponse.ErrorResponse
import com.party.common.ServerApiResponse.ExceptionResponse
import com.party.data.datasource.remote.search.SearchRemoteSource
import com.party.data.mapper.SearchMapper.mapperSearch
import com.party.domain.model.search.Search
import com.party.domain.repository.SearchRepository
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteSource: SearchRemoteSource,
): SearchRepository{
    override suspend fun searchPartiesByKeyword(
        titleSearch: String,
        page: Int,
        limit: Int
    ): ServerApiResponse<Search> {
        return when(val result = searchRemoteSource.search(titleSearch, page, limit)){
            is ApiResponse.Success -> {
                ServerApiResponse.SuccessResponse(data = mapperSearch(result.data))
            }
            is ApiResponse.Failure.Error -> { ErrorResponse() }

            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse(result.message)
            }
        }
    }
}