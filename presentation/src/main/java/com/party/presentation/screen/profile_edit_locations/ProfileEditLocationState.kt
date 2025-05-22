package com.party.presentation.screen.profile_edit_locations

import com.party.domain.model.user.detail.Location
import com.party.domain.model.user.detail.UserLikeLocation

data class ProfileEditLocationState(
    val selectedProvince: String = "서울",

    // province를 클릭하면 우측에 보여줄 Location 목록
    val getLocationList: List<Location> = emptyList(),

    // 유저가 서버에서 불러온 관심 지역 리스트
    //val getUserLikeLocationList: List<UserLikeLocation> = emptyList(),

    // 현재까지 선택한 Location (3개 제한)
    val selectedLocationList: List<Location> = emptyList(),

    // Province + Location을 저장 (UI에서 하단에 모두 표시)
    val selectedProvinceAndLocationList: List<Pair<String, Location>> = emptyList()
)
