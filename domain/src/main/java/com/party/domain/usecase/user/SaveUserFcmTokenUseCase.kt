package com.party.domain.usecase.user

import com.party.domain.model.user.SaveUserFcmTokenRequest
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserFcmTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(saveUserFcmTokenRequest: SaveUserFcmTokenRequest) = userRepository.saveUserFcmToken(saveUserFcmTokenRequest = saveUserFcmTokenRequest)
}