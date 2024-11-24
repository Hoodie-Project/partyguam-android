package com.party.presentation.screen.party_create

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.component.bottomsheet.OneSelectBottomSheet
import com.party.common.component.bottomsheet.list.partyTypeList
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.input_field.MultiLineInputField
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.party_create.component.PartyCreateCustomShape
import com.party.presentation.screen.party_create.component.PartyCreateDescriptionArea
import com.party.presentation.screen.party_create.component.PartyCreateHelpCard
import com.party.presentation.screen.party_create.component.PartyCreateInputField
import com.party.presentation.screen.party_create.component.PartyCreateScaffoldArea
import com.party.presentation.screen.party_create.component.PartyCreateValidField
import com.party.presentation.screen.party_create.component.PartyImageUploadArea
import com.party.presentation.screen.party_create.viewmodel.PartyCreateViewModel

@Composable
fun PartyCreateScreen(
    context: Context,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    partyCreateViewModel: PartyCreateViewModel,
) {
    PartyCreateContent(
        snackBarHostState = snackBarHostState,
        onNavigationClick = { navController.popBackStack()},
    )
}

@Composable
fun PartyCreateContent(
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
) {
    val scrollState  = rememberScrollState()
    var isVisibleToolTip by remember {
        mutableStateOf(true)
    }

    var inputPartyTitle by remember {
        mutableStateOf("")
    }

    var selectedPartyType by remember {
        mutableStateOf("")
    }

    var isPartyTypeSheetOpen by rememberSaveable { mutableStateOf(false) }

    var isHelpCardOpen by remember {
        mutableStateOf(true)
    }

    var partyDescription by remember {
        mutableStateOf("")
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            PartyCreateScaffoldArea(
                onNavigationClick = { onNavigationClick() },
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE)
                .verticalScroll(scrollState)
        ) {
            HeightSpacer(heightDp = 12.dp)

            PartyImageUploadArea(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            if(isVisibleToolTip){
                PartyCreateCustomShape(
                    onClick = { isVisibleToolTip = false }
                )
            }

            HeightSpacer(heightDp = 32.dp)
            PartyCreateDescriptionArea(
                title = "파티명",
                description = "직관적인 파티명을 사용하시면 조회수가 올라가요."
            )

            HeightSpacer(heightDp = 20.dp)
            PartyCreateInputField(
                inputText = inputPartyTitle,
                placeHolder = "15자 이내로 입력해 주세요.",
                readOnly = false,
                icon = {
                    DrawableIconButton(
                        icon = painterResource(id = R.drawable.close),
                        contentDescription = "",
                        onClick = { inputPartyTitle = "" }
                    )
                },
                onValueChange = { inputText ->
                    if(inputText.length <= 15){
                        inputPartyTitle = inputText
                    }
                }
            )

            HeightSpacer(heightDp = 12.dp)
            PartyCreateValidField(
                count = inputPartyTitle.length
            )

            HeightSpacer(heightDp = 30.dp)
            PartyCreateDescriptionArea(
                title = "파티 유형",
                description = "파티가 목표로 하는 유형을 선택해 주세요."
            )

            HeightSpacer(heightDp = 20.dp)
            PartyCreateInputField(
                inputText = selectedPartyType,
                placeHolder = "유형을 선택해 주세요.",
                readOnly = true,
                icon = {
                    DrawableIconButton(
                        icon = painterResource(id = R.drawable.arrow_down_icon),
                        contentDescription = "",
                        onClick = {
                            isPartyTypeSheetOpen = true
                        }
                    )
                },
                onValueChange = {}
            )

            HeightSpacer(heightDp = 30.dp)
            PartyCreateDescriptionArea(
                title = "파티 소개글",
                description = "파티의 방향성, 참고사항 등을 자유롭게 적어주세요.",
                icon = {
                    DrawableIconButton(
                        icon = painterResource(id = R.drawable.help),
                        contentDescription = "",
                        onClick = { isHelpCardOpen = true },
                        modifier = Modifier.size(20.dp)
                    )
                }
            )

            if(isHelpCardOpen){
                HeightSpacer(heightDp = 12.dp)
                PartyCreateHelpCard(
                    onClose = {isHelpCardOpen = false}
                )
            }

            HeightSpacer(heightDp = 20.dp)
            MultiLineInputField(
                placeHolder = "새로운 프로젝트를 위해 모여 함께 아이디어를 나누고 계획을 세우는 파티를 개최합니다.",
                inputText = partyDescription,
                onValueChange = {partyDescription = it},
                onAllDeleteInputText = {partyDescription = ""}
            )

            HeightSpacer(heightDp = 30.dp)
            PartyCreateDescriptionArea(
                title = "내 포지션",
                description = "파티 내에서 본인의 포지션을 선택해주세요."
            )

            HeightSpacer(heightDp = 20.dp)
        }

        if(isPartyTypeSheetOpen){
            OneSelectBottomSheet(
                bottomSheetTitle = "파티 유형",
                contentList = partyTypeList,
                selectedText = selectedPartyType,
                onBottomSheetClose = { isPartyTypeSheetOpen = false },
                onApply = {
                    selectedPartyType = it
                    isPartyTypeSheetOpen = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PartyCreateContentPreview() {
    PartyCreateContent(
        snackBarHostState = SnackbarHostState(),
        onNavigationClick = {}
    )
}