package com.party.domain.usecase.user.detail

import com.party.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserLocationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.deleteInterestLocation()
}