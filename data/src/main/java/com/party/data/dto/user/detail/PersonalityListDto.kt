package com.party.data.dto.user.detail

import com.party.data.DataMapper
import com.party.domain.model.user.detail.PersonalityList
import com.party.domain.model.user.detail.PersonalityListOption
import kotlinx.serialization.Serializable

@Serializable
data class PersonalityListDto(
    val id: Int,
    val content: String,
    val responseCount: String,
    val personalityOptions: List<PersonalityListOptionDto>
): DataMapper<PersonalityList>{
    override fun toDomain(): PersonalityList {
        return PersonalityList(
            id = id,
            content = content,
            responseCount = responseCount,
            personalityOptions = personalityOptions.map { it.toDomain() }
        )
    }
}

@Serializable
data class PersonalityListOptionDto(
    val id: Int,
    val personalityQuestionId: Int,
    val content: String,
): DataMapper<PersonalityListOption>{
    override fun toDomain(): PersonalityListOption {
        return PersonalityListOption(
            id = id,
            personalityQuestionId = personalityQuestionId,
            content = content
        )
    }
}
