package com.party.domain.model.user.detail

import android.os.Parcelable
import kotlinx.serialization.Serializable
import kotlinx.parcelize.Parcelize

@Serializable
@Parcelize
data class PersonalitySaveRequest(
    val personality: List<PersonalitySaveRequest2>
): Parcelable

@Serializable
@Parcelize
data class PersonalitySaveRequest2(
    val personalityQuestionId: Int,
    val personalityOptionId: List<Int>,
): Parcelable
