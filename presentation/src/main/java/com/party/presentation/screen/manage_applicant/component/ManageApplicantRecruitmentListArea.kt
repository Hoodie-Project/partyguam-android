package com.party.presentation.screen.manage_applicant.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.component.RecruitmentListItem5
import com.party.domain.model.party.PartyRecruitment
import com.party.presentation.screen.manage_applicant.ManageApplicantState

@Composable
fun ManageApplicantRecruitmentListArea(
    manageApplicantState: ManageApplicantState,
    onClick: (Int, String, String) -> Unit,
) {
    when {
        manageApplicantState.isLoadingRecruitment -> LoadingProgressBar()
        manageApplicantState.partyRecruitment.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                HeightSpacer(60.dp)
                Text("모집공고가 없어요")
            }
        }
        else -> {
            RecruitmentList(
                list = manageApplicantState.partyRecruitment,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun RecruitmentList(
    list: List<PartyRecruitment>,
    onClick: (Int, String, String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(
            items = list,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            RecruitmentListItem5(
                status = item.status,
                id = item.id,
                createdAt = item.createdAt,
                main = item.position.main,
                sub = item.position.sub,
                applicationCount = item.applicationCount,
                onClick = onClick,
            )
        }
    }
}