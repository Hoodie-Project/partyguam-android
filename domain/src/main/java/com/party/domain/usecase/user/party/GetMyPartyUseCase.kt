package com.party.domain.usecase.user.party

import com.party.domain.repository.UserRepository
import javax.inject.Inject

class GetMyPartyUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(page: Int, limit: Int, sort: String, order: String) =
        userRepository.getMyParties(page, limit, sort, order)
}