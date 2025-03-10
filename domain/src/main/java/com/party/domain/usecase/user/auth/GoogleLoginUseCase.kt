package com.party.domain.usecase.user.auth

import com.party.common.ServerApiResponse
import com.party.domain.model.user.AccessTokenRequest
import com.party.domain.model.user.SocialLogin
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class GoogleLoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
){
    suspend operator fun invoke(accessTokenRequest: AccessTokenRequest): ServerApiResponse<SocialLogin> {
        return userRepository.googleLogin(accessTokenRequest = accessTokenRequest)
    }
}