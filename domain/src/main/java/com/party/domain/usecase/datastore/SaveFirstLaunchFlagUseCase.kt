package com.party.domain.usecase.datastore

import com.party.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveFirstLaunchFlagUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() = dataStoreRepository.saveFirstLaunchFlag()
}