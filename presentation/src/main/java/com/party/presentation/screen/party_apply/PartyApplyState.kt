package com.party.presentation.screen.party_apply

import com.party.domain.model.party.PartyApply

data class PartyApplyState(
    val inputApplyReason: String = "",
    val isShowBackDialog: Boolean = false,

    val isLoading: Boolean = false,
    val partyApply: PartyApply = PartyApply("", "", "2024-06-05T15:30:45.123Z", 0),
)
