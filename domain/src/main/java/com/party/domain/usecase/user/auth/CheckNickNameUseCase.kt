package com.party.domain.usecase.user.auth

import com.party.domain.repository.UserRepository
import javax.inject.Inject

class CheckNickNameUseCase @Inject constructor(
    private val userRepository: UserRepository,
){
    suspend operator fun invoke(signupAccessToken: String, nickname: String) = userRepository.checkNickName(signupAccessToken = signupAccessToken, nickname = nickname)
}