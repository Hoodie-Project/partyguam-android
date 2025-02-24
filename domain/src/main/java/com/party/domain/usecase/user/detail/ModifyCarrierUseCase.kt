package com.party.domain.usecase.user.detail

import com.party.domain.model.user.detail.ModifyCarrierList
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class ModifyCarrierUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(career: ModifyCarrierList) = userRepository.modifyCarrier(career = career)
}