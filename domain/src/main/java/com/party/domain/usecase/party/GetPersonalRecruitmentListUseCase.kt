package com.party.domain.usecase.party

import com.party.common.ServerApiResponse
import com.party.domain.model.party.PersonalRecruitmentList
import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class GetPersonalRecruitmentListUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
){
    suspend operator fun invoke(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ): ServerApiResponse<PersonalRecruitmentList>{
        return partyRepository.getPersonalizedParties(page = page, size = size, sort = sort, order = order)
    }
}