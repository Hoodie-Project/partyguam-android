package com.party.presentation.screen.party_edit_recruitment.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.component.RecruitmentListItem4
import com.party.common.component.icon.DrawableIconButton
import com.party.domain.model.party.PartyRecruitment
import com.party.presentation.screen.party_edit_recruitment.PartyRecruitmentEditState

@Composable
fun PartyRecruitmentListArea(
    partyRecruitmentEditState: PartyRecruitmentEditState,
    onClick: (Int) -> Unit,
    onMoreClick: (Int, String) -> Unit,
) {
    when {
        partyRecruitmentEditState.isLoadingPartyRecruitment -> LoadingProgressBar()
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
                list = partyRecruitmentEditState.partyRecruitment,
                onClick = onClick,
                onMoreClick = onMoreClick
            )
        }
    }
}

@Composable
private fun RecruitmentList(
    list: List<PartyRecruitment>,
    onClick: (Int) -> Unit,
    onMoreClick: (Int, String) -> Unit,
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
        ) { index, item ->
            RecruitmentListItem4(
                status = item.status,
                createdAt = item.createdAt,
                main = item.position.main,
                sub = item.position.sub,
                recruitedCount = item.recruitedCount,
                recruitingCount = item.recruitingCount,
                applicationCount = item.applicationCount,
                onClick = { onClick(item.id) },
                icon = { onClick ->
                    DrawableIconButton(
                        icon = painterResource(id = R.drawable.icon_vertical_more2),
                        contentDescription = "more",
                        onClick = onClick, // 클릭 이벤트 전달
                        modifier = Modifier.size(24.dp)
                    )
                },
                onMoreClick = {
                    onMoreClick(item.id, item.status)
                }
            )
        }
    }
}