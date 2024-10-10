package com.party.domain.usecase.user.detail

import com.party.domain.model.user.detail.InterestLocationRequest
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class SaveInterestLocationUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(accessToken: String, locations: List<InterestLocationRequest>) = userRepository.saveInterestLocation(accessToken = accessToken, locations = locations)
}