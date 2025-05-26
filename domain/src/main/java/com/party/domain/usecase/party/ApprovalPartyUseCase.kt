package com.party.domain.usecase.party

import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class ApprovalPartyUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {
    suspend operator fun invoke(partyId: Int, partyApplicationId: Int) = partyRepository.approvalParty(partyId = partyId, partyApplicationId = partyApplicationId)
}