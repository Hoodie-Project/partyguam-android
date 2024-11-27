package com.party.presentation.screen.party_detail.tab.recruitment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ServerApiResponse
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.snackBarMessage
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.Position1
import com.party.domain.model.user.PartyAuthority
import com.party.presentation.screen.home.tab_main.ErrorArea
import com.party.presentation.screen.party_detail.component.PartyDetailTitleArea
import com.party.presentation.screen.party_detail.tab.recruitment.component.PartyDetailRecruitmentFilterArea
import com.party.presentation.screen.party_detail.tab.recruitment.component.PartyDetailRecruitmentListArea

@Composable
fun PartyDetailRecruitmentArea(
    snackBarHostState: SnackbarHostState,
    partyRecruitmentState: UIState<ServerApiResponse<List<PartyRecruitment>>>,
    partyAuthorityState: UIState<ServerApiResponse<PartyAuthority>>,
    selectedPosition: String,
    onReset: () -> Unit,
    onApply: (String) -> Unit,
) {
    // 등록일 순 내림 차순
    var selectedCreateDataOrderByDesc by remember {
        mutableStateOf(true)
    }

    var authority by remember {
        mutableStateOf(
            PartyAuthority(
                userId = 0,
                authority = ""
            )
        )
    }

    when(partyAuthorityState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val successResult = partyAuthorityState.data as SuccessResponse<PartyAuthority>
            authority = successResult.data ?: PartyAuthority(
                userId = 0,
                authority = ""
            )
        }
        is UIState.Error -> { }
        is UIState.Exception -> { snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState) }
    }

    when(partyRecruitmentState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val successResult = partyRecruitmentState.data as SuccessResponse<List<PartyRecruitment>>
            PartyDetailRecruitmentAreaContent(
                authority = authority,
                list = successResult.data ?: emptyList(),
                selectedCreateDataOrderByDesc = selectedCreateDataOrderByDesc,
                onChangeSelected = { selectedCreateDataOrderByDesc = it },
                selectedPosition = selectedPosition,
                onReset = onReset,
                onApply = onApply,
            )
        }
        is UIState.Error -> { ErrorArea() }
        is UIState.Exception -> { snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState) }
    }
}

@Composable
fun PartyDetailRecruitmentAreaContent(
    list: List<PartyRecruitment>,
    authority: PartyAuthority,
    selectedCreateDataOrderByDesc: Boolean,
    onChangeSelected: (Boolean) -> Unit,
    selectedPosition: String,
    onReset: () -> Unit,
    onApply: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        PartyDetailTitleArea(
            title = "모집공고",
            number = "${list.size}"
        )
        HeightSpacer(heightDp = 16.dp)
        PartyDetailRecruitmentFilterArea(
            selectedPosition = selectedPosition,
            selectedCreateDataOrderByDesc = selectedCreateDataOrderByDesc,
            onChangeSelected = onChangeSelected,
            onReset = onReset,
            onApply = onApply,
        )
        HeightSpacer(heightDp = 8.dp)
        PartyDetailRecruitmentListArea(
            authority = authority,
            selectedCreateDataOrderByDesc = selectedCreateDataOrderByDesc,
            list = list
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PartyDetailRecruitmentAreaContentPreview() {
    PartyDetailRecruitmentAreaContent(
        list = listOf(
            PartyRecruitment(
                id = 2293,
                position = Position1(
                    main = "개발자",
                    sub = "안드로이드"
                ),
                content = "error",
                recruitingCount = 3,
                recruitedCount = 2,
                applicationCount = 1,
                createdAt = "2024-10-25T21:38:28.850Z"
            ),
            PartyRecruitment(
                id = 2293,
                position = Position1(
                    main = "개발자",
                    sub = "안드로이드"
                ),
                content = "error",
                recruitingCount = 3,
                recruitedCount = 2,
                applicationCount = 1,
                createdAt = "2024-10-25T21:38:28.850Z"
            )
        ),
        selectedCreateDataOrderByDesc = true,
        onChangeSelected = {},
        onReset = {},
        onApply = {},
        selectedPosition = "",
        authority = PartyAuthority(
            userId = 1,
            authority = "master"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PartyDetailRecruitmentAreaContentPreview1() {
    PartyDetailRecruitmentAreaContent(
        list = listOf(

        ),
        selectedCreateDataOrderByDesc = true,
        onChangeSelected = {},
        onReset = {},
        onApply = {},
        selectedPosition = "",
        authority = PartyAuthority(
            userId = 1,
            authority = "master"
        )
    )
}