package com.party.domain.usecase.user.notification

import com.party.domain.repository.UserRepository
import javax.inject.Inject

class GetNotificationUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        limit: Int,
        type: String?,
    ) = userRepository.getNotifications(limit = limit, type = type)
}