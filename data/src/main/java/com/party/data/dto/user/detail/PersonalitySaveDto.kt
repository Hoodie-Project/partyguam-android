package com.party.data.dto.user.detail

import com.party.data.DataMapper
import com.party.domain.model.user.detail.PersonalitySave
import kotlinx.serialization.Serializable

@Serializable
data class PersonalitySaveDto(
    val id: Int,
    val userId: Int,
    val personalityOptionId: Int,
): DataMapper<PersonalitySave>{
    override fun toDomain(): PersonalitySave {
        return PersonalitySave(
            id = id,
            userId = userId,
            personalityOptionId = personalityOptionId
        )
    }
}