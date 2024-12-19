package com.party.domain.usecase.party

import com.party.domain.model.party.DelegatePartyMasterRequest
import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class DelegatePartyMasterUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {
    suspend operator fun invoke(partyId: Int, delegatePartyMasterRequest: DelegatePartyMasterRequest) = partyRepository.changeMaster(partyId = partyId, delegatePartyMasterRequest = delegatePartyMasterRequest)
}