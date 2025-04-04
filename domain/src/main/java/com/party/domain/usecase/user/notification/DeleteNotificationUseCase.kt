package com.party.domain.usecase.user.notification

import com.party.domain.repository.UserRepository
import javax.inject.Inject

class DeleteNotificationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        notificationId: Int
    ) = userRepository.deleteNotification(notificationId = notificationId)
}