package com.party.domain.model.party

import kotlinx.serialization.Serializable

@Serializable
data class DelegatePartyMasterRequest(
    val partyUserId: Int,
)
