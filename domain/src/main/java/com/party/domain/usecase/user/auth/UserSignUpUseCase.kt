package com.party.domain.usecase.user.auth

import com.party.common.ServerApiResponse
import com.party.domain.model.user.signup.UserSignUpRequest
import com.party.domain.model.user.signup.UserSignUp
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class UserSignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
){
    suspend operator fun invoke(signupAccessToken: String, userSignUpRequest: UserSignUpRequest): ServerApiResponse<UserSignUp>{
        return userRepository.userSignUp(signupAccessToken = signupAccessToken, userSignUpRequest = userSignUpRequest)
    }
}