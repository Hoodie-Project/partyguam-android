package com.party.presentation.screen.party_user_manage

import com.party.domain.model.party.PartyMemberInfo

data class PartyUserState(
    val inputText: String = "",
    val isOpenPositionBottomSheet: Boolean = false,
    val selectedMainPosition: String = "",
    val isDesc: Boolean = true,
    val isLoading: Boolean = false,
    val partyUserList: List<PartyMemberInfo> = emptyList(),
    val manageBottomSheet: Boolean = false
)
