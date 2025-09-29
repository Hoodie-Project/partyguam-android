package com.party.presentation.screen.profile_edit_career

import com.party.domain.model.user.detail.GetCarrier
import com.party.domain.model.user.detail.PositionList

data class ProfileEditCareerState(

    // 경력/포지션 화면을 보여줄지 , 경력/포지션 선택 화면을 보여줄지
    val isShowPrevScreen: Boolean = true,

    // 주포지션 선택화면인지 부포지션 선택화면인지
    val isMainPosition: Boolean = true,

    val subPositionList: List<PositionList> = emptyList(), // 서브 포지션 리스트


    // 선택된 주포지션
    val getMainPosition: GetCarrier? = null,

    // 선택된 부포지션
    val getSubPosition: GetCarrier? = null,

    val userNickName: String = "***",
)
