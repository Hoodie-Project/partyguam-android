package com.party.presentation.screen.party_edit_recruitment.component

import androidx.compose.foundation.layout.Box
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
import com.party.common.component.RecruitmentListItem4
import com.party.domain.model.party.PartyRecruitment
import com.party.presentation.screen.party_edit_recruitment.PartyRecruitmentEditState

@Composable
fun PartyRecruitmentListArea(
    partyRecruitmentEditState: PartyRecruitmentEditState,
) {
    when {
        partyRecruitmentEditState.isLoadingPartyRecruitment -> {
            LoadingProgressBar()
        }
        partyRecruitmentEditState.partyRecruitment.isEmpty() -> {
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
                list = partyRecruitmentEditState.partyRecruitment
            )
        }
    }
}

@Composable
private fun RecruitmentList(
    list: List<PartyRecruitment>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        itemsIndexed(
            items = list,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            RecruitmentListItem4(
                createdAt = item.createdAt,
                main = item.position.main,
                sub = item.position.sub,
                recruitedCount = item.recruitedCount,
                recruitingCount = item.recruitingCount,
                applicationCount = item.applicationCount,
            )
        }
    }
}