package com.party.presentation.screen.recruitment_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.party.common.ApplyButtonArea
import com.party.common.HeightSpacer
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.convertToText
import com.party.common.ui.theme.GRAY100
import com.party.domain.model.party.RecruitmentDetail
import com.party.presentation.screen.recruitment_detail.component.RecruitmentCurrentInfoArea
import com.party.presentation.screen.recruitment_detail.component.RecruitmentDescription
import com.party.presentation.screen.recruitment_detail.component.RecruitmentImageArea
import com.party.presentation.screen.recruitment_detail.component.RecruitmentPositionAndCountArea
import com.party.presentation.screen.recruitment_detail.viewmodel.RecruitmentDetailViewModel

@Composable
fun RecruitmentDetailScreen(
    partyRecruitmentId: Int,
    recruitmentDetailViewModel: RecruitmentDetailViewModel = hiltViewModel(),
    onClick: () -> Unit,
) {

    LaunchedEffect(Unit) {
        recruitmentDetailViewModel.getRecruitmentDetail(partyRecruitmentId)
    }

    val result by recruitmentDetailViewModel.getRecruitmentDetailState.collectAsStateWithLifecycle()

    when(result){
        is UIState.Idle -> {}
        is UIState.Loading -> {}
        is UIState.Success -> {
            val resultData = result.data as SuccessResponse
            RecruitmentDetailScreenContent(
                recruitmentDetail = resultData.data ?: return,
                onClick = onClick
            )
        }
        is UIState.Error -> {}
        is UIState.Exception -> {}
    }
}

@Composable
fun RecruitmentDetailScreenContent(
    recruitmentDetail: RecruitmentDetail,
    onClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            RecruitmentImageArea(
                imageUrl = recruitmentDetail.party.image,
                title = recruitmentDetail.party.title,
                tag = recruitmentDetail.party.tag,
                type = recruitmentDetail.party.partyType.type,
            )
            HeightSpacer(heightDp = 20.dp)
            RecruitmentCurrentInfoArea(
                recruitingCount = convertToText(recruitmentDetail.recruitedCount, recruitmentDetail.recruitingCount),
                recruitedCount = recruitmentDetail.recruitingCount,
                createDate = recruitmentDetail.createdAt,
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
                position = "${recruitmentDetail.position.main} ${recruitmentDetail.position.sub}",
                recruitingCount = "${recruitmentDetail.recruitingCount}명",
            )
            HeightSpacer(heightDp = 56.dp)
            RecruitmentDescription(
                content = recruitmentDetail.content,
            )
        }

        HeightSpacer(heightDp = 24.dp)
        ApplyButtonArea(
            onClick = onClick
        )
        HeightSpacer(heightDp = 12.dp)

    }
}