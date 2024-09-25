package com.party.domain.usecase.user

import com.party.common.ServerApiResponse
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class GoogleLoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
){
    suspend operator fun invoke(accessToken: String): ServerApiResponse {
        return userRepository.googleLogin(accessToken = accessToken)
    }
}