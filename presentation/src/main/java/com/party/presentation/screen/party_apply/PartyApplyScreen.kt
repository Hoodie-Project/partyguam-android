package com.party.presentation.screen.party_apply

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.party.common.ApplyButtonArea
import com.party.common.HeightSpacer
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.party_apply.component.PartyApplyInputReasonArea
import com.party.presentation.screen.party_apply.component.PartyApplyScaffoldArea
import com.party.presentation.screen.party_apply.component.PartyApplyTitleArea

@Composable
fun PartyApplyScreen(
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    partyId: Int,
    partyRecruitmentId: Int,
) {
    var inputText by remember { mutableStateOf("") }

    PartyApplyScreenContent(
        snackBarHostState = snackBarHostState,
        inputText = inputText,
        onValueChange = { inputText = it },
        onAllDeleteInputText = { inputText = "" },
        onNavigationClick = {navController.popBackStack()}
    )
}

@Composable
fun PartyApplyScreenContent(
    snackBarHostState: SnackbarHostState,
    inputText: String,
    onValueChange: (String) -> Unit,
    onAllDeleteInputText: () -> Unit,
    onNavigationClick: () -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            PartyApplyScaffoldArea(
                onNavigationClick = { onNavigationClick() },
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
                HeightSpacer(heightDp = 40.dp)
                PartyApplyTitleArea()
                HeightSpacer(heightDp = 20.dp)
                PartyApplyInputReasonArea(
                    inputText = inputText,
                    onValueChange = onValueChange,
                    onAllDeleteInputText = onAllDeleteInputText
                )
            }

            ApplyButtonArea(
                containerColor = if(inputText.isNotEmpty()) PRIMARY else LIGHT400,
                contentColor = if(inputText.isNotEmpty()) BLACK else GRAY400,
                borderColor = if(inputText.isNotEmpty()) PRIMARY else LIGHT200,
                onClick = { }
            )
            HeightSpacer(heightDp = 12.dp)

        }
    }

}

@Preview
@Composable
fun PartyApplyScreenContentPreview() {
    PartyApplyScreenContent(
        snackBarHostState = SnackbarHostState(),
        inputText = "지원합니다",
        onValueChange = {},
        onAllDeleteInputText = {},
        onNavigationClick = {}
    )
}