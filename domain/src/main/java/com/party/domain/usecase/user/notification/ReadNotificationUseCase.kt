package com.party.domain.usecase.user.notification

import com.party.domain.repository.UserRepository
import javax.inject.Inject

class ReadNotificationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        notificationId: Int
    ) = userRepository.readNotification(notificationId)
}