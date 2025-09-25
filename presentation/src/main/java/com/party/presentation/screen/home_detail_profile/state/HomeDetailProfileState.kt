package com.party.presentation.screen.home_detail_profile.state

import com.party.domain.model.user.detail.Location
import com.party.domain.model.user.detail.PersonalityList
import com.party.domain.model.user.detail.PersonalityListOption
import com.party.domain.model.user.detail.PositionList
import com.party.presentation.screen.detail.detail_profile.provinceList

data class HomeDetailProfileState(
    val isShowFinishDialog: Boolean = false,

    val selectedProvince: String = provinceList[0], // 선택된 메인 지역 (서울)
    val subLocationList: List<Location> = emptyList(), // 선택된 서브 지역 리스트
    val selectedProvinceAndSubLocationList: List<Pair<String, Location>> = emptyList(), // 메인 지역 + 서브지역 리스트

    // 경력/포지션 선택
    val firstCareer: String = "",
    val firstMainPosition: String = "",
    val firstSubPosition: String = "",
    val firstSubPositionId: Int = 0,
    val secondCareer: String = "",
    val secondMainPosition: String = "",
    val secondSubPosition: String = "",
    val secondSubPositionId: Int = 0,

    val subPositionList: List<PositionList> = emptyList(),

    // 전체 성향 질문 리스트
    val personalityList: List<PersonalityList> = emptyList(),

    val selectedTraitList1: List<PersonalityListOption> = emptyList(),
    val selectedTraitList2: List<PersonalityListOption> = emptyList(),
    val selectedTraitList3: List<PersonalityListOption> = emptyList(),
    val selectedTraitList4: List<PersonalityListOption> = emptyList(),

)