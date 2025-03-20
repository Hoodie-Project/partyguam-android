package com.party.domain.usecase.user

import com.party.domain.repository.UserRepository
import javax.inject.Inject

class RecoverAuthUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(recoverAccessToken: String) = userRepository.recoverAuth(recoverAccessToken = recoverAccessToken)
}