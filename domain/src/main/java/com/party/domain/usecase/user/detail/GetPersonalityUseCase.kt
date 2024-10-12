package com.party.domain.usecase.user.detail

import com.party.common.ServerApiResponse
import com.party.domain.model.user.detail.PersonalityListResponse
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class GetPersonalityUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(accessToken: String): ServerApiResponse<List<PersonalityListResponse>> {
        return userRepository.getPersonalities(accessToken = accessToken)
    }
}