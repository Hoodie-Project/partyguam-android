package com.party.domain.usecase.party

import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class GetPartyRecruitmentUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
) {
    suspend operator fun invoke(partyId: Int, sort: String, order: String, main: String?) = partyRepository.getPartyRecruitmentList(partyId = partyId, sort = sort, order = order, main = main)
}