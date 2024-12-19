package com.party.domain.usecase.party

import com.party.domain.model.party.ModifyRecruitmentRequest
import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class ModifyRecruitmentUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {
    suspend operator fun invoke(partyId: Int, partyRecruitmentId: Int, modifyRecruitmentRequest: ModifyRecruitmentRequest) = partyRepository.modifyRecruitment(partyId = partyId, partyRecruitmentId = partyRecruitmentId, modifyRecruitmentRequest = modifyRecruitmentRequest)
}