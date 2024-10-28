package com.party.domain.usecase.user.detail

import com.party.common.ServerApiResponse
import com.party.domain.model.user.detail.PositionListResponse
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class GetPositionsUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(main: String): ServerApiResponse<List<PositionListResponse>> {
        return userRepository.getPositions(main = main)
    }
}