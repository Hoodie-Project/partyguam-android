package com.party.presentation.screen.recruitment_detail

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.utils.HeightSpacer
import com.party.common.utils.LoadingProgressBar
import com.party.common.utils.convertToText
import com.party.guam.design.GRAY100
import com.party.guam.design.WHITE
import com.party.domain.model.user.PartyAuthority
import com.party.domain.model.user.PartyAuthorityPosition
import com.party.common.component.BottomNavigationBar
import com.party.common.Screens
import com.party.presentation.enum.PartyAuthorityType
import com.party.presentation.screen.recruitment_detail.component.RecruitmentButton
import com.party.presentation.screen.recruitment_detail.component.RecruitmentCurrentInfoArea
import com.party.presentation.screen.recruitment_detail.component.RecruitmentDescription
import com.party.presentation.screen.recruitment_detail.component.RecruitmentImageArea
import com.party.presentation.screen.recruitment_detail.component.RecruitmentPositionAndCountArea
import com.party.presentation.screen.recruitment_detail.component.RecruitmentScaffoldArea
import com.party.presentation.screen.recruitment_detail.viewmodel.RecruitmentDetailViewModel

@Composable
fun RecruitmentDetailRoute(
    context: Context,
    navController: NavHostController,
    partyId: Int,
    partyRecruitmentId: Int,
    recruitmentDetailViewModel: RecruitmentDetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        recruitmentDetailViewModel.getRecruitmentDetail(partyRecruitmentId = partyRecruitmentId)
        recruitmentDetailViewModel.checkUserApplicationStatus(partyId = partyId, partyRecruitmentId = partyRecruitmentId)
        recruitmentDetailViewModel.getPartyAuthority(partyId = partyId)
    }

    val recruitmentDetailState by recruitmentDetailViewModel.recruitmentDetailState.collectAsStateWithLifecycle()

    RecruitmentDetailScreen(
        context = context,
        navController = navController,
        recruitmentDetailState = recruitmentDetailState,
        onManageClick = {
            navController.navigate(Screens.RecruitmentEdit(partyId = partyId, partyRecruitmentId = partyRecruitmentId))
        },
        onGotoPartyDetail = {
            navController.navigate(Screens.PartyDetail(partyId = partyId))
        },
        onAction = { action ->
            when(action){
                is RecruitmentDetailAction.OnNavigationBack -> { navController.popBackStack() }
                is RecruitmentDetailAction.OnApply -> { navController.navigate(Screens.PartyApply(partyId = partyId, partyRecruitmentId = partyRecruitmentId)) }
            }
        },
    )
}

@Composable
fun RecruitmentDetailScreen(
    context: Context,
    navController: NavHostController,
    recruitmentDetailState: RecruitmentDetailState,
    onManageClick: () -> Unit,
    onGotoPartyDetail: () -> Unit,
    onAction: (RecruitmentDetailAction) -> Unit
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
            RecruitmentScaffoldArea(
                recruitmentDetailState = recruitmentDetailState,
                onNavigationClick = { onAction(RecruitmentDetailAction.OnNavigationBack) },
                onSharedClick = {},
                onManageClick = onManageClick,
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
        ) {
            if(recruitmentDetailState.isLoading){
                LoadingProgressBar()
            }else {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                ) {
                    RecruitmentImageArea(
                        imageUrl = recruitmentDetailState.recruitmentDetail.party.image,
                        title = recruitmentDetailState.recruitmentDetail.party.title,
                        tag = recruitmentDetailState.recruitmentDetail.party.status,
                        type = recruitmentDetailState.recruitmentDetail.party.partyType.type,
                        onGoToPartyDetail = onGotoPartyDetail
                    )
                    HeightSpacer(heightDp = 20.dp)
                    RecruitmentCurrentInfoArea(
                        recruitingCount = convertToText(recruitmentDetailState.recruitmentDetail.applicationCount, recruitmentDetailState.recruitmentDetail.recruitingCount),
                        applicationCount = recruitmentDetailState.recruitmentDetail.applicationCount,
                        createDate = recruitmentDetailState.recruitmentDetail.createdAt,
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
                        position = "${recruitmentDetailState.recruitmentDetail.position.main} ${recruitmentDetailState.recruitmentDetail.position.sub}",
                        recruitingCount = "${recruitmentDetailState.recruitmentDetail.recruitingCount}명",
                    )
                    HeightSpacer(heightDp = 56.dp)
                    RecruitmentDescription(
                        content = recruitmentDetailState.recruitmentDetail.content,
                    )
                }

                if (recruitmentDetailState.partyAuthority.authority !in listOf(PartyAuthorityType.MASTER.authority, PartyAuthorityType.MEMBER.authority)) {
                    RecruitmentButton(
                        isRecruitment = recruitmentDetailState.isRecruitment,
                        onClick = { onAction(RecruitmentDetailAction.OnApply) },
                    )
                }

                HeightSpacer(heightDp = 12.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecruitmentDetailScreenPreview1() {
    RecruitmentDetailScreen(
        context = LocalContext.current,
        navController = rememberNavController(),
        recruitmentDetailState = RecruitmentDetailState(
            partyAuthority = PartyAuthority(id = 0, authority = "member", position = PartyAuthorityPosition(0, "", ""))
        ),
        onAction = {},
        onManageClick = {},
        onGotoPartyDetail = {},
    )
}

@Preview(showBackground = true)
@Composable
fun RecruitmentDetailScreenPreview2() {
    RecruitmentDetailScreen(
        context = LocalContext.current,
        navController = rememberNavController(),
        recruitmentDetailState = RecruitmentDetailState(
            partyAuthority = PartyAuthority(id = 0, authority = "", position = PartyAuthorityPosition(0, "", ""))
        ),
        onAction = {},
        onManageClick = {},
        onGotoPartyDetail = {},
    )
}