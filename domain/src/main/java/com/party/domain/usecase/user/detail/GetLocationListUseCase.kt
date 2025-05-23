package com.party.domain.usecase.user.detail

import com.party.common.ServerApiResponse
import com.party.domain.model.user.detail.Location
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class GetLocationListUseCase @Inject constructor(
    private val userRepository: UserRepository,
){
    suspend operator fun invoke(province: String): ServerApiResponse<List<Location>> {
        return userRepository.getLocations(province = province)
    }
}