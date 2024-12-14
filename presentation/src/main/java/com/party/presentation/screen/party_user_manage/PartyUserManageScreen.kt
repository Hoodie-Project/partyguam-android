package com.party.presentation.screen.party_user_manage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.party_user_manage.component.PartyUserCountArea
import com.party.presentation.screen.party_user_manage.component.PartyUserFilterArea
import com.party.presentation.screen.party_user_manage.component.PartyUserScaffoldArea
import com.party.presentation.screen.party_user_manage.component.PartyUserSearchArea

@Composable
fun PartyUserManageScreenRoute(
    modifier: Modifier = Modifier
) {

}

@Composable
private fun PartyUserManageScreen(
    snackBarHostState: SnackbarHostState,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            PartyUserScaffoldArea(
                onNavigationClick = {}
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
            // 파티원
            HeightSpacer(heightDp = 16.dp)
            PartyUserCountArea()
            HeightSpacer(18.dp)

            // 검색
            PartyUserSearchArea(
                inputText = "",
                placeHolder = "닉네임을 검색해 보세요.",
                onValueChange = {}
            )

            // 필터
            HeightSpacer(heightDp = 16.dp)
            PartyUserFilterArea(
                isPartyTypeFilterClick = {},
                isDesc = true,
                onChangeOrderBy = {},
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyUserManageScreenPreview() {
    PartyUserManageScreen(
        snackBarHostState = SnackbarHostState()
    )
}