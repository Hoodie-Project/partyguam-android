package com.party.presentation.screen.party_edit

import com.party.domain.model.user.detail.PositionList

data class PartyEditState(
    val isLoadingPartyDetail: Boolean = false,
    val isVisibleToolTip: Boolean = true, // 툴팁

    val partyStatus: String = "", // 파티 상태

    val inputPartyTitle: String = "", // 파티 제목

    val selectedPartyType: String = "", // 선택된 파티 타입 (미정, 스터디, 포트폴리오, 해커톤, 공모전)
    val isPartyTypeSheetOpen: Boolean = false, // 파티 타입 시트 오픈 여부

    val partyDescription: String = "", // 파티 설명
    val isHelpCardOpen: Boolean = false, // 도움말 카드

    val isMainPositionBottomSheetShow: Boolean = false, // 포지션 선택 바텀시트
    val selectedMainPosition: String = "", // 선택된 메인 포지션
    val selectedSubPosition: PositionList = PositionList(0, "", ""), // 선택된 서브 포지션
    val subPositionList: List<PositionList> = emptyList(), // 서브 포지션 리스트
)
