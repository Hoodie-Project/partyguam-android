package com.party.presentation.screen.party_user_manage

import com.party.domain.model.party.PartyMemberInfo

data class PartyUserState(
    val inputText: String = "",
    val isOpenPositionBottomSheet: Boolean = false,
    val selectedMainPosition: String = "",
    val isDesc: Boolean = true,
    val isLoading: Boolean = false,
    val partyUserList: List<PartyMemberInfo> = emptyList(),
    val manageBottomSheet: Boolean = false,
    val selectedMemberAuthority: String = "", // 유저 리스트 클릭시 해당 유저가 마스터인지 일반 유저인지 판단하기 위한 변수
)
