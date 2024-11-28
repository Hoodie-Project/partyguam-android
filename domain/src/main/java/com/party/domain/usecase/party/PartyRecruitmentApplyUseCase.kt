package com.party.domain.usecase.party

import com.party.common.ServerApiResponse
import com.party.domain.model.party.PartyApply
import com.party.domain.model.party.PartyApplyRequest
import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class PartyRecruitmentApplyUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
){
    suspend operator fun invoke(
        partyId: Int,
        partyRecruitmentId: Int,
        partyApplyRequest: PartyApplyRequest,
    ): ServerApiResponse<PartyApply>{
        return partyRepository.applyPartyRecruitment(partyId, partyRecruitmentId, partyApplyRequest)
    }
}