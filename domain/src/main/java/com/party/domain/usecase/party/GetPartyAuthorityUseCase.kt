package com.party.domain.usecase.party

import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class GetPartyAuthorityUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
) {
    suspend operator fun invoke(partyId: Int) = partyRepository.getPartyAuthority(partyId = partyId)
}