package com.party.domain.usecase.user

import com.party.domain.repository.UserRepository
import javax.inject.Inject

class CheckVersionUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(platform: String) = userRepository.checkVersion(platform = platform)
}