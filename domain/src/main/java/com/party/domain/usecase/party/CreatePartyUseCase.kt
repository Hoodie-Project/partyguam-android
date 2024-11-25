package com.party.domain.usecase.party

import com.party.domain.model.party.PartyCreateRequest
import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class CreatePartyUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {
    suspend operator fun invoke(partyCreateRequest: PartyCreateRequest) = partyRepository.saveParty(partyCreateRequest = partyCreateRequest)
}