package com.party.data.repository

import com.party.common.ServerApiResponse
import com.party.common.ServerApiResponse.ErrorResponse
import com.party.common.ServerApiResponse.ExceptionResponse
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.data.datasource.remote.party.PartyRemoteSource
import com.party.data.mapper.PartyMapper.mapperPersonalRecruitmentResponse
import com.party.domain.model.party.PersonalRecruitmentListResponse
import com.party.domain.repository.PartyRepository
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class PartyRepositoryImpl @Inject constructor(
    private val partyRemoteSource: PartyRemoteSource,
): PartyRepository{
    override suspend fun getPersonalizedParties(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ): ServerApiResponse<PersonalRecruitmentListResponse> {
        return when(val result = partyRemoteSource.getPersonalizedParties(page, size, sort, order)){
            is ApiResponse.Success -> {
                SuccessResponse(data = mapperPersonalRecruitmentResponse(result.data))
            }
            is ApiResponse.Failure.Error -> {
                ErrorResponse()
            }
            is ApiResponse.Failure.Exception -> {
                ExceptionResponse(
                    result.message,
                )
            }
        }
    }
}