package com.party.domain.usecase.user.detail

import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class SavePersonalityUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(personalitySaveRequest: PersonalitySaveRequest) = userRepository.savePersonalities(personalitySaveRequest = personalitySaveRequest)
}

class SavePersonalityUseCaseV2 @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(personalitySaveRequest: PersonalitySaveRequest) = userRepository.savePersonalitiesV2(personalitySaveRequest = personalitySaveRequest)
}