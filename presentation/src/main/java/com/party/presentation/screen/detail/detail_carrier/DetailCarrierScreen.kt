package com.party.presentation.screen.detail.detail_carrier

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.party.common.R
import com.party.common.ScreenExplainArea
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.PRIMARY
import com.party.presentation.screen.detail.ProfileIndicatorArea

@Composable
fun DetailCarrierScreen(
    context: Context,
    snackBarHostState: SnackbarHostState,
    navController: NavController,
    detailCarrierViewModel: DetailCarrierViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        detailCarrierViewModel.getAccessToken().join()
    }
    val accessToken by detailCarrierViewModel.accessToken.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            ProfileIndicatorArea(
                container1 = PRIMARY,
                container2 = PRIMARY,
                container3 = GRAY100,
                textColor1 = BLACK,
                textColor2 = BLACK,
                textColor3 = GRAY400,
                indicatorText = stringResource(id = R.string.detail_profile3),
            )

            ScreenExplainArea(
                mainExplain = stringResource(id = R.string.detail_carrier1),
                subExplain = stringResource(id = R.string.detail_carrier2),
            )

            PositionArea(
                context = context,
                navController = navController,
                accessToken = accessToken,
            )
        }

        DetailCarrierBottomArea(
            context = context,
            snackBarHostState = snackBarHostState,
            navController = navController,
            detailCarrierViewModel = detailCarrierViewModel,
            accessToken = accessToken,
        )
    }
}