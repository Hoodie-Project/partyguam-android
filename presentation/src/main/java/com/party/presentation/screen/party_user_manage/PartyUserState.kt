package com.party.presentation.screen.party_user_manage

import com.party.domain.model.party.PartyMemberInfo
import com.party.domain.model.user.detail.PositionList

data class PartyUserState(
    val inputText: String = "",
    val isOpenPositionBottomSheet: Boolean = false,
    val selectedMainPosition: String = "전체",
    val isDesc: Boolean = true,
    val isLoading: Boolean = false,
    val partyUserList: List<PartyMemberInfo> = emptyList(), // 첫 통신시 받아온 유저 리스트 - 계속 유지함
    val filteredPartyUserList: List<PartyMemberInfo> = emptyList(), // 사용자에게 보여지는 것은 필터 리스트
    val manageBottomSheet: Boolean = false,
    val selectedMemberAuthority: String = "", // 유저 리스트 클릭시 해당 유저가 마스터인지 일반 유저인지 판단하기 위한 변수
    val selectedPartyMemberId: Int = 0, // 유저 리스트 클릭시 해당 유저의 파티 id

    val isMainPositionBottomSheetShow: Boolean = false,
    val getSubPositionList: List<PositionList> = emptyList(),
    val selectedSubPosition: PositionList = PositionList(0, "", ""), // 선택된 서브 포지션

    val isShowModifyDialog: Boolean = false,
)
