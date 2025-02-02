package com.party.domain.usecase.user.auth

import com.party.domain.model.user.AccessTokenRequest
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class LinkGoogleUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(accessTokenRequest: AccessTokenRequest) = userRepository.linkGoogle(accessTokenRequest = accessTokenRequest)
}