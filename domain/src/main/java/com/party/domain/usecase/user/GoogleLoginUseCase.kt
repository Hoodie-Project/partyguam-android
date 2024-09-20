package com.party.domain.usecase.user

import com.party.common.BaseSuccessResponse
import com.party.common.ServerApiResponse
import com.party.domain.model.member.SocialLoginRequest
import com.party.domain.model.member.SocialLoginResponse
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class GoogleLoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
){
    suspend operator fun invoke(socialLoginRequest: SocialLoginRequest): ServerApiResponse {
        return userRepository.googleLogin(socialLoginRequest = socialLoginRequest)
    }
}