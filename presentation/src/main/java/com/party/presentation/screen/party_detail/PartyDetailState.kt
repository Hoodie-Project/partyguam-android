package com.party.presentation.screen.party_detail

import com.party.common.component.partyDetailTabList
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.PartyType
import com.party.domain.model.party.PartyUsers
import com.party.domain.model.user.PartyAuthority
import com.party.domain.model.user.PartyAuthorityPosition

data class PartyDetailState(
    val selectedTabText: String = partyDetailTabList[0],

    val isLoadingPartyDetail: Boolean = false,
    val partyDetail: PartyDetail = PartyDetail(
        id = 0,
        partyType = PartyType(id = 0, type = ""),
        title = "",
        content = "",
        image = null,
        status = "",
        createdAt = "2024-06-05T15:30:45.123Z",
        updatedAt = "2024-06-05T15:30:45.123Z"
    ),

    val isLoadingPartyUser: Boolean = false,
    val partyUser: PartyUsers = PartyUsers(
        partyAdmin = emptyList(),
        partyUser = emptyList()
    ),

    val isLoadingPartyRecruitment: Boolean = false,
    val partyRecruitment: List<PartyRecruitment> = emptyList(),
    val selectedOrderBy: Boolean = true,
    val isShowPositionFilter: Boolean = false,
    val selectedPosition: String = "전체",
    val isProgress: Boolean = true,

    val isLoadingPartyAuthority: Boolean = false,
    val partyAuthority: PartyAuthority = PartyAuthority(id = 0, authority = "", position = PartyAuthorityPosition(0, "", "")),

    val isShowMoreBottomSheet: Boolean = false,
    val isShowExitPartyDialog: Boolean = false,
)
