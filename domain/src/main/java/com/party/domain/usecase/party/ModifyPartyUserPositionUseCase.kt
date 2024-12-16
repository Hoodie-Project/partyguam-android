package com.party.domain.usecase.party

import com.party.domain.model.party.ModifyPartyUserPositionRequest
import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class ModifyPartyUserPositionUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {
    suspend operator fun invoke(partyId: Int, userId: Int, modifyPartyUserPositionRequest: ModifyPartyUserPositionRequest) = partyRepository.modifyPartyMemberPosition(partyId, userId, modifyPartyUserPositionRequest)
}