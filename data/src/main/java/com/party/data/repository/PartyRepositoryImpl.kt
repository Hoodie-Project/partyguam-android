package com.party.data.repository

import com.party.common.ServerApiResponse
import com.party.common.ServerApiResponse.ErrorResponse
import com.party.common.ServerApiResponse.ExceptionResponse
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.data.datasource.remote.party.PartyRemoteSource
import com.party.data.mapper.PartyMapper.mapperPartyAuthority
import com.party.data.mapper.PartyMapper.mapperPartyDetail
import com.party.data.mapper.PartyMapper.mapperPartyRecruitment
import com.party.data.mapper.PartyMapper.mapperPartyResponse
import com.party.data.mapper.PartyMapper.mapperPartyUsers
import com.party.data.mapper.PartyMapper.mapperPersonalRecruitmentResponse
import com.party.data.mapper.PartyMapper.mapperRecruitmentDetailResponse
import com.party.data.mapper.PartyMapper.mapperToPartyCreate
import com.party.domain.model.party.PartyCreate
import com.party.domain.model.party.PartyCreateRequest
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyList
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.PartyUsers
import com.party.domain.model.party.PersonalRecruitmentList
import com.party.domain.model.party.RecruitmentDetail
import com.party.domain.model.party.RecruitmentList
import com.party.domain.model.user.PartyAuthority
import com.party.domain.repository.PartyRepository
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class PartyRepositoryImpl @Inject constructor(
    private val partyRemoteSource: PartyRemoteSource,
) : PartyRepository {
    override suspend fun getPersonalizedParties(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ): ServerApiResponse<PersonalRecruitmentList> {
        return when (val result =
            partyRemoteSource.getPersonalizedParties(page, size, sort, order)) {
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

    override suspend fun getRecruitmentList(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ): ServerApiResponse<RecruitmentList> {
        return when (val result = partyRemoteSource.getRecruitmentList(page, size, sort, order)) {
            is ApiResponse.Success -> {
                SuccessResponse(data = mapperRecruitmentDetailResponse(result.data))
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

    override suspend fun getPartyList(
        page: Int,
        size: Int,
        sort: String,
        order: String,
        partyTypes: List<Int>,
    ): ServerApiResponse<PartyList> {
        return when(val result = partyRemoteSource.getPartyList(
            page = page,
            size = size,
            sort = sort,
            order = order,
            partyTypes = partyTypes
        )){
            is ApiResponse.Success -> {
                SuccessResponse(data = mapperPartyResponse(result.data))
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

    override suspend fun getRecruitmentDetail(partyRecruitmentId: Int): ServerApiResponse<RecruitmentDetail> {
        return when(val result = partyRemoteSource.getRecruitmentDetail(partyRecruitmentId = partyRecruitmentId)){
            is ApiResponse.Success -> {
                SuccessResponse(data = mapperRecruitmentDetailResponse(result.data))
            }
            is ApiResponse.Failure.Error -> { ErrorResponse() }

            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse()
            }
        }
    }

    override suspend fun getPartyDetail(partyId: Int): ServerApiResponse<PartyDetail> {
        return when(val result = partyRemoteSource.getPartyDetail(partyId = partyId)){
            is ApiResponse.Success -> {
                SuccessResponse(data = mapperPartyDetail(result.data))
            }
            is ApiResponse.Failure.Error -> { ErrorResponse() }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse()
            }
        }
    }

    override suspend fun getPartyUsers(
        partyId: Int,
        page: Int,
        limit: Int,
        sort: String,
        order: String
    ): ServerApiResponse<PartyUsers> {
        return when(val result = partyRemoteSource.getPartyUsers(partyId = partyId, page = page, limit = limit, sort = sort, order = order)){
            is ApiResponse.Success -> {
                SuccessResponse(data = mapperPartyUsers(result.data))
            }
            is ApiResponse.Failure.Error -> { ErrorResponse() }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse()
            }
        }
    }

    override suspend fun getPartyRecruitmentList(
        partyId: Int,
        sort: String,
        order: String,
        main: String?,
    ): ServerApiResponse<List<PartyRecruitment>> {
        return when(val result = partyRemoteSource.getPartyRecruitmentList(partyId = partyId, sort = sort, order = order, main = main)){
            is ApiResponse.Success -> {
                SuccessResponse(data = result.data.map {
                    mapperPartyRecruitment(it)
                })
            }
            is ApiResponse.Failure.Error -> { ErrorResponse() }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse()
            }

        }
    }

    override suspend fun getPartyAuthority(partyId: Int): ServerApiResponse<PartyAuthority> {
        return when(val result = partyRemoteSource.getPartyAuthority(partyId = partyId)){
            is ApiResponse.Success -> {
                SuccessResponse(data = mapperPartyAuthority(result.data))
            }
            is ApiResponse.Failure.Error -> { ErrorResponse() }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse()
            }
        }
    }

    override suspend fun saveParty(partyCreateRequest: PartyCreateRequest): ServerApiResponse<PartyCreate> {
        return when(val result = partyRemoteSource.createParty(partyCreateRequest = partyCreateRequest)){
            is ApiResponse.Success -> {
                SuccessResponse(data = mapperToPartyCreate(result.data))
            }
            is ApiResponse.Failure.Error -> { ErrorResponse() }
            is ApiResponse.Failure.Exception -> {
                result.throwable.printStackTrace()
                ExceptionResponse()
            }
        }
    }
}