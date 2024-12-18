package com.party.domain.usecase.party

import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class DeletePartyMemberUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {
    suspend operator fun invoke(partyId: Int, partyUserId: Int) = partyRepository.deletePartyMember(partyId = partyId, partyUserId = partyUserId)
}