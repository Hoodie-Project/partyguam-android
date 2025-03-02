package com.party.presentation.screen.profile_edit_career

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.profile_edit_career.component.ProfileEditCareerScaffoldArea
import com.party.presentation.screen.profile_edit_career.component.ProfileEditResetAndAddButtonArea
import com.party.presentation.screen.profile_edit_career.component.ProfileEditSelectCareerArea
import com.party.presentation.screen.profile_edit_career.component.ProfileEditSelectPositionArea
import com.party.presentation.screen.profile_edit_career.component.ProfileEditSubPositionArea

@Composable
fun SelectCareerAndPositionScreen(
    selectedCareer: String,
    selectedMainPosition: String,
    selectedSubPosition: String,
    userProfileState: ProfileEditCareerState,
    onNavigationClick: () -> Unit,
    onAction: (ProfileEditCareerAction) -> Unit,
    onAdd: (String, String, String, Int) -> Unit
) {
    var selectedYears by remember { mutableStateOf(selectedCareer) }
    var selectedMain by remember { mutableStateOf(selectedMainPosition) }
    var selectedSub by remember { mutableStateOf(selectedSubPosition) }
    var selectedSubId by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            ProfileEditCareerScaffoldArea(
                title = if(userProfileState.isMainPosition) "주포지션" else "부포지션",
                onNavigationClick = onNavigationClick
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                ProfileEditSelectCareerArea(
                    selectedCareer = selectedYears,
                    onSelectCareer = {
                        selectedYears = it
                    },
                )
                HeightSpacer(heightDp = 60.dp)

                // 메인 포지션 선택
                ProfileEditSelectPositionArea(
                    selectedMainPosition = selectedMain,
                    onSelectMainPosition = { mainPosition ->
                        selectedMain = mainPosition
                        onAction(ProfileEditCareerAction.OnGetSubPositionList(mainPosition))
                    },
                )

                // 서브 포지션
                HeightSpacer(heightDp = 20.dp)
                ProfileEditSubPositionArea(
                    userProfileState = userProfileState,
                    selectedSubPosition = selectedSub,
                    onSelectSubPosition = {
                        selectedSub = it.sub
                        selectedSubId = it.id
                    }
                )
            }

            // 초기화, 추가하기 버튼
            ProfileEditResetAndAddButtonArea(
                onReset = {
                    selectedYears = ""
                    selectedMain = ""
                    selectedSub = ""
                    selectedSubId = 0
                },
                onAdd = {
                    onAdd(
                        selectedYears,
                        selectedMain,
                        selectedSub,
                        selectedSubId
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectCareerAndPositionScreenPreview() {
    SelectCareerAndPositionScreen(
        selectedCareer = "경력",
        selectedMainPosition = "개발자",
        selectedSubPosition = "안드로이드",
        userProfileState = ProfileEditCareerState(),
        onNavigationClick = {},
        onAction = {},
        onAdd = { _, _, _, _ -> }
    )
}