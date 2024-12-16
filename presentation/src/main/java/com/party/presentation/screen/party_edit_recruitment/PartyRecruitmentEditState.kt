package com.party.presentation.screen.party_edit_recruitment

import com.party.domain.model.party.PartyRecruitment

data class PartyRecruitmentEditState(
    val isShowHelpCard: Boolean = false,
    val isDesc: Boolean = false,

    val isLoadingPartyRecruitment: Boolean = false,
    val partyRecruitment: List<PartyRecruitment> = emptyList(),
)
