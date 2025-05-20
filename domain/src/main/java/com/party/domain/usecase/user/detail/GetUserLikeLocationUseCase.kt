package com.party.domain.usecase.user.detail

import com.party.common.ServerApiResponse
import com.party.domain.model.user.detail.UserLikeLocation
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class GetUserLikeLocationUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(): ServerApiResponse<List<UserLikeLocation>>{
        return userRepository.getUserLikeLocations()
    }
}