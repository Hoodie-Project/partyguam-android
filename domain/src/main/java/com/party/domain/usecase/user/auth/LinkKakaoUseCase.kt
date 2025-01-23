package com.party.domain.usecase.user.auth

import com.party.domain.model.user.LinkKakaoRequest
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class LinkKakaoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(linkKakaoRequest: LinkKakaoRequest) = userRepository.linkKakao(linkKakaoRequest = linkKakaoRequest)
}