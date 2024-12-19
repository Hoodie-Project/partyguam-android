package com.party.presentation.screen.party_edit_recruitment.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.party.common.ActionIcon
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.SwipeItemWithActions
import com.party.common.component.RecruitmentListItem4
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.domain.model.party.PartyRecruitment
import com.party.presentation.screen.party_edit_recruitment.PartyRecruitmentEditState

@Composable
fun PartyRecruitmentListArea(
    partyRecruitmentEditState: PartyRecruitmentEditState,
    onExpanded: (Int) -> Unit,
    onCollapsed: (Int) -> Unit,
    onDelete: (Int) -> Unit,
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
                onExpanded = onExpanded,
                onCollapsed = onCollapsed,
                onDelete = onDelete,
            )
        }
    }
}

@Composable
private fun RecruitmentList(
    list: List<PartyRecruitment>,
    onExpanded: (Int) -> Unit,
    onCollapsed: (Int) -> Unit,
    onDelete: (Int) -> Unit,
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
            SwipeItemWithActions(
                isRevealed = item.isOptionsRevealed,
                onExpanded = { onExpanded(index) },
                onCollapsed = { onCollapsed(index) },
                actions = {
                    ActionIcon(
                        onClick = { onDelete(item.id) },
                        backgroundColor = Color(0XFFFF6262),
                        icon = Icons.Default.Delete,
                    )
                },
                content = {
                    RecruitmentListItem4(
                        createdAt = item.createdAt,
                        main = item.position.main,
                        sub = item.position.sub,
                        recruitedCount = item.recruitedCount,
                        recruitingCount = item.recruitingCount,
                        applicationCount = item.applicationCount,
                    )
                }
            )
        }
    }
}