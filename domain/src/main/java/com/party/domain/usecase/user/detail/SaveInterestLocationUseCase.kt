package com.party.domain.usecase.user.detail

import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class SaveInterestLocationUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(locations: InterestLocationList) = userRepository.saveInterestLocation(locations = locations)}

class SaveInterestLocationUseCaseV2 @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(locations: InterestLocationList) = userRepository.saveInterestLocationV2(locations = locations)
}