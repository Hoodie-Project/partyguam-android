package com.party.presentation.screen.recruitment_preview

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.component.BottomNavigationBar
import com.party.common.convertToText
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.recruitment_detail.component.RecruitmentCurrentInfoArea
import com.party.presentation.screen.recruitment_detail.component.RecruitmentDescription
import com.party.presentation.screen.recruitment_detail.component.RecruitmentImageArea
import com.party.presentation.screen.recruitment_detail.component.RecruitmentPositionAndCountArea
import com.party.presentation.screen.recruitment_preview.component.RecruitmentPreviewScaffoldArea
import com.party.presentation.screen.recruitment_preview.viewmodel.RecruitmentPreviewViewModel

@Composable
fun RecruitmentPreviewScreenRoute(
    context: Context,
    navController: NavHostController,
    partyRecruitmentId: Int,
    description: String,
    recruitingCount: Int,
    main: String,
    sub: String,
    recruitmentPreviewViewModel: RecruitmentPreviewViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        recruitmentPreviewViewModel.getRecruitmentDetail(partyRecruitmentId = partyRecruitmentId)
        recruitmentPreviewViewModel.updateInfo(
            description = description,
            recruitingCount = recruitingCount,
            main = main,
            sub = sub,
        )
    }

    val state by recruitmentPreviewViewModel.recruitmentPreviewState.collectAsStateWithLifecycle()

    RecruitmentPreviewScreen(
        context = context,
        navController = navController,
        state = state,
        onNavigationClick = { navController.popBackStack()}
    )

}

@Composable
private fun RecruitmentPreviewScreen(
    context: Context,
    navController: NavHostController,
    state: RecruitmentPreviewState,
    onNavigationClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                context = context,
                navController = navController,
            )
        },
        topBar = {
            RecruitmentPreviewScaffoldArea(
                onNavigationClick = onNavigationClick
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                RecruitmentImageArea(
                    imageUrl = state.recruitmentDetail.party.image,
                    title = state.recruitmentDetail.party.title,
                    tag = state.recruitmentDetail.party.status,
                    type = state.recruitmentDetail.party.partyType.type,
                    onGoToPartyDetail = {}
                )
                HeightSpacer(heightDp = 20.dp)
                RecruitmentCurrentInfoArea(
                    recruitingCount = convertToText(state.recruitmentDetail.recruitedCount, state.recruitingCount),
                    recruitedCount = state.recruitmentDetail.applicationCount,
                    createDate = state.recruitmentDetail.createdAt,
                )
                HeightSpacer(heightDp = 32.dp)
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = GRAY100,
                    thickness = 12.dp
                )
                HeightSpacer(heightDp = 32.dp)
                RecruitmentPositionAndCountArea(
                    position = "${state.main} ${state.sub}",
                    recruitingCount = "${state.recruitingCount}명",
                )
                HeightSpacer(heightDp = 56.dp)
                RecruitmentDescription(
                    content = state.description,
                )
            }

            HeightSpacer(heightDp = 24.dp)
        }
    }
}