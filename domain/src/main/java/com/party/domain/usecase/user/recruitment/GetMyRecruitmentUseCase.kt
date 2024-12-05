package com.party.domain.usecase.user.recruitment

import com.party.domain.repository.UserRepository
import javax.inject.Inject

class GetMyRecruitmentUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        page: Int,
        limit: Int,
        sort: String,
        order: String,
    ) = userRepository.getMyRecruitments(page, limit, sort, order)
}