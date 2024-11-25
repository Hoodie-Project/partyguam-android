package com.party.presentation.screen.party_create

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.UIState
import com.party.common.component.bottomsheet.OneSelectBottomSheet
import com.party.common.component.bottomsheet.component.ApplyButton
import com.party.common.component.bottomsheet.list.partyTypeList
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.input_field.MultiLineInputField
import com.party.common.noRippleClickable
import com.party.common.snackBarMessage
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PartyCreateRequest
import com.party.domain.model.user.detail.PositionListResponse
import com.party.presentation.screen.home.viewmodel.HomeViewModel
import com.party.presentation.screen.party_create.component.PartyCreateCustomShape
import com.party.presentation.screen.party_create.component.PartyCreateDescriptionArea
import com.party.presentation.screen.party_create.component.PartyCreateHelpCard
import com.party.presentation.screen.party_create.component.PartyCreateInputField
import com.party.presentation.screen.party_create.component.PartyCreateScaffoldArea
import com.party.presentation.screen.party_create.component.PartyCreateSelectPositionArea
import com.party.presentation.screen.party_create.component.PartyCreateValidField
import com.party.presentation.screen.party_create.component.PartyImageUploadArea
import com.party.presentation.screen.party_create.viewmodel.PartyCreateViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PartyCreateScreen(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    homeViewModel: HomeViewModel,
    partyCreateViewModel: PartyCreateViewModel,
) {
    // 다이얼로그 오픈 여부
    var isShowDialog by remember { mutableStateOf(false) }

    // 생성 완료 다이얼로그 오픈 여부
    var isShowCompleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        partyCreateViewModel.createSuccess.collectLatest {
            isShowCompleteDialog = true
        }
    }

    val createPartyState by partyCreateViewModel.createPartyState.collectAsStateWithLifecycle()
    when(createPartyState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar()}
        is UIState.Success -> {}
        is UIState.Error -> {}
        is UIState.Exception -> { snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState) }
    }

    // 선택된 메인 포지션
    var selectedMainPosition by remember { mutableStateOf("") }

    // 선택된 서브 포지션
    var selectedSubPosition by remember { mutableStateOf( PositionListResponse(0, "", "")) }

    PartyCreateContent(
        isShowDialog = isShowDialog,
        onVisibleDialog = { isShowDialog = it },
        isShowCompleteDialog = isShowCompleteDialog,
        onVisibleShowCompleteDialog = {
            isShowCompleteDialog = it
            navController.popBackStack()
        },
        snackBarHostState = snackBarHostState,
        homeViewModel = homeViewModel,
        selectedMainPosition = selectedMainPosition,
        selectedSubPosition = selectedSubPosition,
        onNavigationClick = { navController.popBackStack()},
        onApply = { main, sub ->
            selectedMainPosition = main
            selectedSubPosition = sub
        },
        onCreate = { inputTitle, selectedPartyType, inputContent ->
            partyCreateViewModel.createParty(
                partyCreateRequest = PartyCreateRequest(
                    title = inputTitle,
                    content = inputContent,
                    partyTypeId = partyTypeList.first { it.first == selectedPartyType }.second,
                    positionId = selectedSubPosition.id,
                )
            )
        }
    )
}

@Composable
fun PartyCreateContent(
    isShowDialog: Boolean,
    onVisibleDialog: (Boolean) -> Unit,
    isShowCompleteDialog: Boolean,
    onVisibleShowCompleteDialog: (Boolean) -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    selectedMainPosition: String,
    selectedSubPosition: PositionListResponse,
    onNavigationClick: () -> Unit,
    onApply: (String, PositionListResponse) -> Unit,
    onCreate: (String, String, String) -> Unit,
) {
    val scrollState = rememberScrollState()

    // 툴팁 오픈 여부
    var isVisibleToolTip by remember { mutableStateOf(true) }

    // 입력된 파티 제목
    var inputPartyTitle by remember { mutableStateOf("") }

    // 선택된 파티 유형
    var selectedPartyType by remember { mutableStateOf("") }

    // 파티 유형 시트 오픈 여부
    var isPartyTypeSheetOpen by rememberSaveable { mutableStateOf(false) }

    // 파티 소개글 도움글 오픈 여부
    var isHelpCardOpen by remember { mutableStateOf(true) }

    // 파티 소개글
    var partyDescription by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            PartyCreateScaffoldArea(
                isShowDialog = isShowDialog,
                isShowCompleteDialog = isShowCompleteDialog,
                onNavigationClick = {
                    onVisibleDialog(true)
                }
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .blur(
                    radiusX = if (isShowDialog || isShowCompleteDialog) 10.dp else 0.dp,
                    radiusY = if (isShowDialog || isShowCompleteDialog) 10.dp else 0.dp,
                )
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
            PartyCreateSelectPositionArea(
                snackBarHostState = snackBarHostState,
                homeViewModel = homeViewModel,
                selectedMainPosition = selectedMainPosition,
                selectedSubPosition = selectedSubPosition,
                onApply = onApply
            )

            HeightSpacer(heightDp = 90.dp)

            ApplyButton(
                buttonText = "생성하기",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = { onCreate(inputPartyTitle, selectedPartyType, partyDescription) }
            )
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

        if(isShowDialog){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BLACK.copy(alpha = 0.7f))
                    .noRippleClickable { onVisibleDialog(false) }
            ){
                TwoButtonDialog(
                    dialogTitle = "나가기",
                    description = "입력한 내용들이 모두 초기화됩니다.\n나가시겠습니까?",
                    cancelButtonText = "취소",
                    confirmButtonText = "나가기",
                    onCancel = { onVisibleDialog(false) },
                    onConfirm = {
                        onVisibleDialog(false)
                        onNavigationClick()
                    }
                )
            }
        }

        if(isShowCompleteDialog){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BLACK.copy(alpha = 0.7f))
                    .noRippleClickable { onVisibleShowCompleteDialog(false) }
            ){
                TwoButtonDialog(
                    dialogTitle = "생성 완료",
                    description = "파티가 생성되었어요!\n파티원을 모집해 볼까요?",
                    cancelButtonText = "닫기",
                    confirmButtonText = "모집하기",
                    onCancel = { onVisibleShowCompleteDialog(false) },
                    onConfirm = {
                        onVisibleShowCompleteDialog(false)
                    }
                )
            }

        }
    }
}