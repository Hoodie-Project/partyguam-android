package com.party.data.datasource.remote.party

import com.party.data.entity.party.PartyAuthorityDto
import com.party.data.entity.party.PartyCreateDto
import com.party.data.entity.party.PartyDetailDto
import com.party.data.entity.party.PartyListDto
import com.party.data.entity.party.PartyRecruitmentDto
import com.party.data.entity.party.PartyUsersDto
import com.party.data.entity.party.PersonalRecruitmentListDto
import com.party.data.entity.party.RecruitmentDetailDto
import com.party.data.entity.party.RecruitmentListDto
import com.party.data.service.PartyService
import com.party.domain.model.party.PartyCreateRequest
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PartyRemoteSourceImpl @Inject constructor(
    private val partyService: PartyService,
): PartyRemoteSource{
    override suspend fun getPersonalizedParties(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ): ApiResponse<PersonalRecruitmentListDto> {
        return partyService.getPersonalizedParties(page = page, size = size, sort = sort, order = order)
    }

    override suspend fun getRecruitmentList(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ): ApiResponse<RecruitmentListDto> {
        return partyService.getRecruitmentList(page = page, size = size, sort = sort, order = order)
    }

    override suspend fun getPartyList(
        page: Int,
        size: Int,
        sort: String,
        order: String,
        partyTypes: List<Int>
    ): ApiResponse<PartyListDto> {
        return partyService.getPartyList(page = page, size = size, sort = sort, order = order, partyTypes = partyTypes)
    }

    override suspend fun getRecruitmentDetail(partyRecruitmentId: Int): ApiResponse<RecruitmentDetailDto> {
        return partyService.getRecruitmentDetail(partyRecruitmentId = partyRecruitmentId)
    }

    override suspend fun getPartyDetail(partyId: Int): ApiResponse<PartyDetailDto> {
        return partyService.getPartyDetail(partyId = partyId)
    }

    override suspend fun getPartyUsers(
        partyId: Int,
        page: Int,
        limit: Int,
        sort: String,
        order: String
    ): ApiResponse<PartyUsersDto> {
        return partyService.getPartyUsers(partyId = partyId, page = page, limit = limit, sort = sort, order = order)
    }

    override suspend fun getPartyRecruitmentList(
        partyId: Int,
        sort: String,
        order: String,
        main: String?,
    ): ApiResponse<List<PartyRecruitmentDto>> {
        return partyService.getPartyRecruitmentList(partyId = partyId, sort = sort, order = order, main = main)
    }

    override suspend fun getPartyAuthority(partyId: Int): ApiResponse<PartyAuthorityDto> {
        return partyService.getPartyAuthority(partyId = partyId)
    }

    override suspend fun createParty(
        title: RequestBody,
        content: RequestBody,
        partyTypeId: RequestBody,
        positionId: RequestBody,
        image: MultipartBody.Part
    ): ApiResponse<PartyCreateDto> {
        return partyService.saveParty(
            title = title,
            content = content,
            partyTypeId = partyTypeId,
            positionId = positionId,
            image = image
        )
    }
}