package com.party.presentation.screen.home_detail_profile.state

import com.party.domain.model.user.detail.Location
import com.party.presentation.screen.detail.detail_profile.provinceList

data class HomeDetailProfileState(
    val selectedProvince: String = provinceList[0], // 선택된 메인 지역 (서울)
    val subLocationList: List<Location> = emptyList(),
    val selectedProvinceAndSubLocationList: List<Pair<String, Location>> = emptyList(),
)