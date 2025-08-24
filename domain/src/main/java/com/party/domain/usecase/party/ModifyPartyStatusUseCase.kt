package com.party.domain.usecase.party

import com.party.domain.model.party.ModifyPartyStatusRequest
import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class ModifyPartyStatusUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
){
    suspend operator fun invoke(
        partyId: Int,
        modifyPartyStatusRequest: ModifyPartyStatusRequest,
    ) = partyRepository.modifyPartyStatus(partyId = partyId, modifyPartyStatusRequest = modifyPartyStatusRequest)
}