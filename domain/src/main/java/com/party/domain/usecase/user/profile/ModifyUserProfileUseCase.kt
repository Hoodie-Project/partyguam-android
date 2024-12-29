package com.party.domain.usecase.user.profile

import com.party.domain.model.user.profile.UserProfileRequest
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class ModifyUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userProfileRequest: UserProfileRequest) = userRepository.updateUserProfile(userProfileRequest)
}