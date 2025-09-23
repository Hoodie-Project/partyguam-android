package com.party.presentation.screen.join.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.party.common.Screens
import com.party.common.utils.HeightSpacer
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.T1
import com.party.guam.design.WHITE
import com.party.presentation.screen.join.component.JoinTitleSection
import com.party.presentation.screen.join.component.RowButtonArea

@Composable
fun JoinCompleteScreenRoute(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
){
    JoinCompleteScreen(
        snackBarHostState = snackBarHostState,
        onGotoHome = { navController.navigate(Screens.Home)},
        onGotoDetailProfile = {}
    )
}

@Composable
private fun JoinCompleteScreen(
    snackBarHostState: SnackbarHostState,
    onGotoHome: () -> Unit = {},
    onGotoDetailProfile: () -> Unit = {}
){
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
        ,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
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
                    .fillMaxWidth()
                    .weight(1f)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "가입을 축하합니다!\n세부 프로필을 작성해 볼까요?",
                    fontWeight = FontWeight.Bold,
                    fontSize = T1,
                    textAlign = TextAlign.Center
                )
            }

            RowButtonArea(
                onGotoHome = onGotoHome,
                onGotoDetailProfile = onGotoDetailProfile
            )

            HeightSpacer(heightDp = 12.dp)
        }
    }
}

@Preview
@Composable
private fun JoinCompleteScreenPreview() {
    JoinCompleteScreen(
        snackBarHostState = SnackbarHostState()
    )
}