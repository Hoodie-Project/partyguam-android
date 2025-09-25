package com.party.domain.usecase.user.detail

import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class SaveCarrierUseCase @Inject constructor(
    private val userRepository: UserRepository,
){
    suspend operator fun invoke(career: SaveCarrierList) = userRepository.saveCarrier(career = career)
}

class SaveCareerUseCase @Inject constructor(
    private val userRepository: UserRepository,
){
    suspend operator fun invoke(career: SaveCarrierList) = userRepository.saveCareerV2(career = career)
}