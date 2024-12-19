package com.party.presentation.screen.recruitment_edit

import com.party.domain.model.user.detail.PositionList

data class RecruitmentEditState(
    val isMainPositionBottomSheetShow: Boolean = false, // 포지션 선택 바텀시트
    val selectedMainPosition: String = "", // 선택된 메인 포지션
    val selectedSubPosition: PositionList = PositionList(0, "", ""), // 선택된 서브 포지션
    val subPositionList: List<PositionList> = emptyList(), // 서브 포지션 리스트

    // 모집인원
    val selectedCount: String = "",
    val isPeopleCountSheetOpen: Boolean = false,

    // 모집소개글
    val isHelpCardOpen: Boolean = false,
    val recruitmentDescription: String = "",

    // 모집 생성
    val isRecruitmentCreateLoading: Boolean = false,
)
