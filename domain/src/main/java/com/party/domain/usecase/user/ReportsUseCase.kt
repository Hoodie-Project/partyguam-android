package com.party.domain.usecase.user

import com.party.domain.model.user.ReportsRequest
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class ReportsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(reportsRequest: ReportsRequest) = userRepository.reports(reportsRequest = reportsRequest)
}