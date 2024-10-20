package com.party.data.datasource.remote.party

import com.party.data.entity.party.PersonalRecruitmentListEntity
import com.party.data.service.PartyService
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class PartyRemoteSourceImpl @Inject constructor(
    private val partyService: PartyService,
): PartyRemoteSource{
    override suspend fun getPersonalizedParties(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ): ApiResponse<PersonalRecruitmentListEntity> {
        return partyService.getPersonalizedParties(page = page, size = size, sort = sort, order = order)
    }
}