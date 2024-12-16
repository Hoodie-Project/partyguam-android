package com.party.presentation.screen.party_edit_recruitment.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.party.common.component.chip.OrderByCreateDtChip
import com.party.presentation.screen.party_edit_recruitment.PartyRecruitmentEditState

@Composable
fun PartyRecruitmentEditFilterArea(
    partyRecruitmentEditState: PartyRecruitmentEditState,
    onChangeOrderBy: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OrderByCreateDtChip(
            text = "참여일순",
            orderByDesc = partyRecruitmentEditState.isDesc,
            onChangeSelected = { onChangeOrderBy(it) }
        )
    }
}