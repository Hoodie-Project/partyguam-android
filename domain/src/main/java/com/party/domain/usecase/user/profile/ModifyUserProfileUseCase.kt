package com.party.domain.usecase.user.profile

import com.party.domain.repository.UserRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ModifyUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        image: MultipartBody.Part? = null,
        genderVisible: Boolean? = null,
        birthVisible: Boolean? = null,
        portfolioTitle: RequestBody? = null,
        portfolio: RequestBody? = null
    ) = userRepository.updateUserProfile(
        image = image,
        genderVisible = genderVisible,
        birthVisible = birthVisible,
        portfolioTitle = portfolioTitle,
        portfolio = portfolio
    )
}