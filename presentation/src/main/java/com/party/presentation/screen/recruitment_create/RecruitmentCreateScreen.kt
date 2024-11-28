package com.party.presentation.screen.recruitment_create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.component.bottomsheet.OneSelectPickerBottomSheet
import com.party.common.component.bottomsheet.component.ApplyButton
import com.party.common.component.bottomsheet.list.peopleCountList
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.input_field.MultiLineInputField
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.domain.model.user.detail.PositionList
import com.party.presentation.component.HelpCard
import com.party.presentation.component.SelectMainAndSubPositionArea
import com.party.presentation.screen.home.viewmodel.HomeViewModel
import com.party.presentation.screen.recruitment_create.component.RecruitmentCreateDescriptionArea
import com.party.presentation.screen.recruitment_create.component.RecruitmentCreateInputField
import com.party.presentation.screen.recruitment_create.component.RecruitmentCreateScaffoldArea

@Composable
fun RecruitmentCreateScreen(
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    partyId: Int,
) {
    // 선택된 메인 포지션
    var selectedMainPosition by remember { mutableStateOf("") }

    // 선택된 서브 포지션
    var selectedSubPosition by remember { mutableStateOf(PositionList(0, "", "")) }

    RecruitmentCreateContent(
        snackBarHostState = snackBarHostState,
        homeViewModel = homeViewModel,
        selectedMainPosition = selectedMainPosition,
        selectedSubPosition = selectedSubPosition,
        onBackNavigation = { navController.popBackStack() },
        onApply = { main, sub ->
            selectedMainPosition = main
            selectedSubPosition = sub
        },
    )
}

@Composable
fun RecruitmentCreateContent(
    snackBarHostState: SnackbarHostState,
    homeViewModel: HomeViewModel,
    selectedMainPosition: String,
    selectedSubPosition: PositionList,
    onBackNavigation: () -> Unit,
    onApply: (String, PositionList) -> Unit,
) {
    val scrollState = rememberScrollState()

    // 인원 선택 시트 오픈 여부
    var isPeopleCountSheetOpen by rememberSaveable { mutableStateOf(false) }

    // 선택된 명 수
    var selectedCount by remember { mutableStateOf("") }

    // 입력된 모집 소개글
    var recruitmentDescription by remember { mutableStateOf("") }

    // 파티 소개글 도움글 오픈 여부
    var isHelpCardOpen by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            RecruitmentCreateScaffoldArea(
                onNavigationClick = onBackNavigation
            )
        },

        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WHITE)
                    .padding(it)
                    .padding(horizontal = MEDIUM_PADDING_SIZE)
                    .verticalScroll(scrollState)
            ) {
                HeightSpacer(heightDp = 40.dp)
                RecruitmentCreateDescriptionArea(
                    title = "모집 포지션",
                    description = "모집하시는 포지션을 입력해 주세요."
                )
                HeightSpacer(heightDp = 20.dp)
                SelectMainAndSubPositionArea(
                    snackBarHostState = snackBarHostState,
                    homeViewModel = homeViewModel,
                    selectedMainPosition = selectedMainPosition,
                    selectedSubPosition = selectedSubPosition,
                    onApply = onApply
                )

                HeightSpacer(heightDp = 60.dp)
                RecruitmentCreateDescriptionArea(
                    title = "모집 인원",
                    description = "모집하시는 인원을 선택해 주세요."
                )
                HeightSpacer(heightDp = 20.dp)
                RecruitmentCreateInputField(
                    inputText = selectedCount,
                    placeHolder = "인원을 선택해 주세요.",
                    readOnly = true,
                    icon = {
                        DrawableIconButton(
                            icon = painterResource(id = R.drawable.arrow_down_icon),
                            contentDescription = "",
                            onClick = {
                                isPeopleCountSheetOpen = true
                            }
                        )
                    },
                    onValueChange = {}
                )
                if(isPeopleCountSheetOpen){
                    OneSelectPickerBottomSheet(
                        bottomSheetTitle = "모집 인원",
                        selectedText = selectedCount,
                        peopleCountList = peopleCountList,
                        onBottomSheetClose = { isPeopleCountSheetOpen = false },
                        onApply = {
                            selectedCount = it
                            isPeopleCountSheetOpen = false
                        }
                    )
                }

                HeightSpacer(heightDp = 60.dp)
                RecruitmentCreateDescriptionArea(
                    title = "모집 소개글",
                    description = "파티의 방향성, 참고사항 등을 자유롭게 적어 주세요.",
                    icon = {
                        DrawableIconButton(
                            icon = painterResource(id = R.drawable.help),
                            contentDescription = "",
                            onClick = { isHelpCardOpen = true },
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )
                if (isHelpCardOpen) {
                    HeightSpacer(heightDp = 12.dp)
                    HelpCard(
                        description1 = "현재 파티의 진행상태는 어떤가요?",
                        description2 = "어떤 사람을 구인하시나요? (툴, 포르폴리오 등)",
                        onClose = { isHelpCardOpen = false }
                    )
                }
                HeightSpacer(heightDp = 20.dp)
                MultiLineInputField(
                    placeHolder = "새로운 프로젝트를 위해 모여 함께 아이디어를 나누고 계획을 세우는 파티를 개최합니다.",
                    inputText = recruitmentDescription,
                    onValueChange = { recruitmentDescription = it },
                    onAllDeleteInputText = { recruitmentDescription = "" }
                )

                HeightSpacer(heightDp = 60.dp)
                ApplyButton(
                    buttonText = "모집하기",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = {  }
                )
                HeightSpacer(heightDp = 20.dp)
            }
        }
    )
}
