package com.party.presentation.screen.profile_edit_locations

import com.party.domain.model.user.detail.Location

data class ProfileEditLocationState(

    val selectedProvince: String = "서울",

    // province 를 클릭했을 때 세부적으로 조회하는 지역 리스트
    val getLocationList: List<Location> = emptyList(),

    // 선택된 location list
    val selectedLocationList: List<Location> = emptyList(),

    // 선택된 province + location list
    val selectedProvinceAndLocationList: List<Pair<String, Location>> = emptyList()
)
