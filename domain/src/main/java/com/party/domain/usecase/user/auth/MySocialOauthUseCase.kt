package com.party.domain.usecase.user.auth

import com.party.domain.repository.UserRepository
import javax.inject.Inject

class MySocialOauthUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.getMySocialOauth()
}