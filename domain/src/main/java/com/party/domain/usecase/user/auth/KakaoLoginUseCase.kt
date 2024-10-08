package com.party.domain.usecase.user.auth

import com.party.common.ServerApiResponse
import com.party.domain.model.user.SocialLoginResponse
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
){
    suspend operator fun invoke(accessToken: String): ServerApiResponse<SocialLoginResponse> {
        return userRepository.kakaoLogin(accessToken = accessToken)
    }
}