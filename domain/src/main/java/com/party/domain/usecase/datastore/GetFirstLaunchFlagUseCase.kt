package com.party.domain.usecase.datastore

import com.party.domain.repository.DataStoreRepository
import javax.inject.Inject

class GetFirstLaunchFlagUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke() = dataStoreRepository.getFirstLaunchFlag()
}